package com.bll.core.bean.tenant.create;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.core.im.tenant.constant.*;
import com.core.im.tenant.modal.order.OrderAddress;
import com.core.im.tenant.modal.order.OrderDetail;
import com.core.im.tenant.modal.order.OrderStatus;
import com.core.im.tenant.modal.product.*;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserAddress;
import com.core.im.tenant.modal.user.UserDetail;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.order.IOrderAddressDao;
import com.cos.core.dao.order.IOrderDetailDao;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.product.*;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserAddressDao;
import com.cos.core.dao.user.IUserDetailDao;
import com.cos.core.dao.user.IUserRoleDao;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@ExtendWith(DBUnitExtension.class)
public class CreateTenantBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;
    }

    //user beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserSet.xml")
    void appUserDaoBeanTest() {
        IAppUserDao appUserDao = context.getBean(IAppUserDao.class);
        AppUser appUser = new AppUser();
        appUser.setEmail("test@gmail.com");
        appUser.setUsername("test");
        appUser.setPassword("password");
        appUserDao.saveEntity(appUser);

        List<AppUser> appUserList = appUserDao.getEntityListBySQLQuery("select * from app_user a;");
        Assertions.assertFalse(appUserList.isEmpty());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserRoleSet.xml")
    void userRoleDaoBeanTest() {
        IUserRoleDao userRoleDao = context.getBean(IUserRoleDao.class);
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.ROLE_USER);
        userRoleDao.saveEntity(userRole);
        List<UserRole> userRoleList = userRoleDao.getEntityListBySQLQuery("select * from user_role u;");
        Assertions.assertFalse(userRoleList.isEmpty());
        Assertions.assertEquals(RoleEnum.ROLE_USER.getValue(), userRoleList.get(0).getRoleName());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserAddressSet.xml")
    void userAddressBeanTest() {
        IUserAddressDao userAddressDao = context.getBean(IUserAddressDao.class);
        UserAddress userAddress = new UserAddress();
        userAddress.setFlor(9);
        userAddress.setBuilding(1);
        userAddress.setApartmentNumber(111);
        userAddress.setStreet("Some Name");
        userAddressDao.saveEntity(userAddress);
        List<UserAddress> userAddressList = userAddressDao.getEntityListBySQLQuery("select * from user_address a;");
        Assertions.assertFalse(userAddressList.isEmpty());
        Assertions.assertEquals("Some Name", userAddressList.get(0).getStreet());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserDetailSet.xml")
    void userDetailDaoBeanTest() {
        IUserDetailDao userDetailDao = context.getBean(IUserDetailDao.class);
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("test");
        userDetailDao.saveEntity(userDetail);
        List<UserDetail> userDetailList =
                userDetailDao.getEntityListBySQLQuery("select * from user_detail u;");
        Assertions.assertFalse(userDetailList.isEmpty());
        Assertions.assertEquals("test", userDetailList.get(0).getFirstName());
    }

    //product beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/product/createExpectedCategorySet.xml")
    void categoryDaoBeanTest() {
        ICategoryDao categoryDao = context.getBean(ICategoryDao.class);
        Category category = new Category();
        category.setCategoryEnum(CategoryEnum.BEAUTY_AND_HEALTH);
        categoryDao.saveEntity(category);
        List<Category> categoryList = categoryDao.getEntityListBySQLQuery("select * from category c;");
        Assertions.assertFalse(categoryList.isEmpty());
        Assertions.assertEquals(CategoryEnum.BEAUTY_AND_HEALTH.getValue(), categoryList.get(0).getName());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/product/createExpectedBrandSet.xml")
    void brandDaoBeanTest() {
        IBrandDao brandDao = context.getBean(IBrandDao.class);
        Brand brand = new Brand();
        brand.setBrandEnum(BrandEnum.APPLE);
        brandDao.saveEntity(brand);
        List<Brand> brandList = brandDao.getEntityListBySQLQuery("select * from brand b;");
        Assertions.assertFalse(brandList.isEmpty());
        Assertions.assertEquals(BrandEnum.APPLE.getValue(), brandList.get(0).getName());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/product/createExpectedDiscountSet.xml")
    void discountDaoBeanTest() {
        IDiscountDao discountDao = context.getBean(IDiscountDao.class);
        Discount discount = new Discount();
        discount.setAmount(10);
        discountDao.saveEntity(discount);
        List<Discount> discountList = discountDao.getEntityListBySQLQuery("select * from discount d;");
        Assertions.assertFalse(discountList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/product/createExpectedProductTypeSet.xml")
    void productTypeDaoBeanTest() {
        IProductTypeDao productTypeDao = context.getBean(IProductTypeDao.class);
        ProductType productType = new ProductType();
        productType.setProductType(ProductTypeEnum.CLAUSE);
        productTypeDao.saveEntity(productType);
        List<ProductType> productTypeList = productTypeDao.getEntityListBySQLQuery("select * from product_type p;");
        Assertions.assertFalse(productTypeList.isEmpty());
        Assertions.assertEquals(ProductTypeEnum.CLAUSE.getValue(), productTypeList.get(0).getName());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/product/createExpectedProductStatusSet.xml")
    void productStatusDaoBeanTest() {
        IProductStatusDao productStatusDao = context.getBean(IProductStatusDao.class);
        ProductStatus productStatus = new ProductStatus();
        productStatus.setName(ProductStatusEnum.NEW);
        productStatusDao.saveEntity(productStatus);
        List<ProductStatus> productStatusList =
                productStatusDao.getEntityListBySQLQuery("select * from product_status p;");
        Assertions.assertFalse(productStatusList.isEmpty());
        Assertions.assertEquals(ProductStatusEnum.NEW, productStatusList.get(0).getName());

    }

    //order beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/order/createExpectedOrderAddressSet.xml")
    void orderAddressDaoBeanTest() {
        IOrderAddressDao orderAddressDao = context.getBean(IOrderAddressDao.class);
        OrderAddress orderAddress = new OrderAddress();
        orderAddress.setApartmentNumber(1);
        orderAddress.setBuilding(1);
        orderAddress.setFlor(1);
        orderAddress.setStreet("Some street");
        orderAddressDao.saveEntity(orderAddress);
        List<OrderAddress> orderAddressList =
                orderAddressDao.getEntityListBySQLQuery("select * from order_address a;");
        Assertions.assertFalse(orderAddressList.isEmpty());
        Assertions.assertEquals("Some street", orderAddressList.get(0).getStreet());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/order/createExpectedOrderDetailSet.xml")
    void orderDetailDaoBeanTest() {
        IOrderDetailDao orderDetailDao = context.getBean(IOrderDetailDao.class);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFirstName("test");
        orderDetailDao.saveEntity(orderDetail);
        List<OrderDetail> orderDetailList =
                orderDetailDao.getEntityListBySQLQuery("select * from order_detail o;");
        Assertions.assertFalse(orderDetailList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/order/createExpectedOrderStatusSet.xml")
    void orderStatusDaoBeanTest() {
        IOrderStatusDao orderStatusDao = context.getBean(IOrderStatusDao.class);
        OrderStatus status = new OrderStatus();
        status.setName(OrderStatusEnum.NEW);
        orderStatusDao.saveEntity(status);
        List<OrderStatus> orderStatusList =
                orderStatusDao.getEntityListBySQLQuery("select * from order_status o;");
        Assertions.assertFalse(orderStatusList.isEmpty());
        Assertions.assertEquals(OrderStatusEnum.NEW, orderStatusList.get(0).getName());

    }

    //to do
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true)
//    void orderItemDaoBeanTest() {
//        IOrderDao<OrderItem> orderDao = context.getBean(IOrderDao.class);
//        OrderItem orderItem = new OrderItem();
//        orderDao.saveEntity(orderItem);
//        List<OrderItem> orderItemList = orderDao.getEntityListBySQLQuery("select * from order_item o;");
//        Assertions.assertFalse(orderItemList.isEmpty());
//
//    }

//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true)
//    void orderHistoryDaoBeanTest() {
//        IOrderHistoryDao<OrderHistory> orderHistoryDao = context.getBean(IOrderHistoryDao.class);
//        OrderHistory orderHistory = new OrderHistory();
//        orderHistory.setDataOfOrder(System.currentTimeMillis());
//        orderHistoryDao.saveEntity(orderHistory);
//        List<OrderHistory> productTypeList =
//                orderHistoryDao.getEntityListBySQLQuery("select * from order_history o;");
//        Assertions.assertFalse(productTypeList.isEmpty());
//
//    }
}
