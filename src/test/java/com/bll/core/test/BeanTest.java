package com.bll.core.test;

import com.bll.core.bean.BeanConfiguration;
import com.core.im.constant.BrandEnum;
import com.core.im.constant.CategoryEnum;
import com.core.im.constant.ProductTypeEnum;
import com.core.im.constant.RoleEnum;
import com.core.im.modal.product.Brand;
import com.core.im.modal.product.Category;
import com.core.im.modal.product.Discount;
import com.core.im.modal.product.ProductType;
import com.core.im.modal.user.*;
import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.dao.product.*;
import com.cos.core.dao.user.*;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@ExtendWith(DBUnitExtension.class)
public class BeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        ConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConnectionPoolType.HIKARI, ConfigDbType.XML
        );
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = configurationSessionFactory.getSessionFactory();
        dataSource = getHikariDataSource();
        connectionHolder = dataSource::getConnection;
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void appUserDaoBeanTest() {
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/initDataSet.xml");
        String[] beanNames = context.getBeanDefinitionNames();
        IAppUserDao<AppUser> appUserDao = context.getBean(IAppUserDao.class);
        List<AppUser> appUserList = appUserDao.getEntityListBySQLQuery("select * from app_user a;");
        Assertions.assertTrue(appUserList.isEmpty());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void userRoleDaoBeanTest() {
        IUserRoleDao<UserRole> userRoleDao = context.getBean(IUserRoleDao.class);
        UserRole userRole = new UserRole();
        userRole.setRoleEnum(RoleEnum.ROLE_USER);
        userRoleDao.saveEntity(userRole);
        List<UserRole> userRoleList = userRoleDao.getEntityListBySQLQuery("select * from user_role u;");
        Assertions.assertFalse(userRoleList.isEmpty());
        Assertions.assertEquals(RoleEnum.ROLE_USER.getValue(), userRoleList.get(0).getRoleName());

    }

    //user beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void userPaymentDaoBeanTest() {
        IUserPaymentDao<UserPayment> userPaymentDao = context.getBean(IUserPaymentDao.class);
        UserPayment userPayment = new UserPayment();
        userPaymentDao.saveEntity(userPayment);
        List<UserPayment> userRoleList = userPaymentDao.getEntityListBySQLQuery("select * from user_payment u;");
        Assertions.assertFalse(userRoleList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void userAddressBeanTest() {
        IUserAddressDao<UserAddress> userAddressDao = context.getBean(IUserAddressDao.class);
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
    void userDetailsDaoBeanTest() {
        IUserDetailsDao<UserDetails> userDetailsDao = context.getBean(IUserDetailsDao.class);
        UserDetails userDetails = new UserDetails();
        userDetailsDao.saveEntity(userDetails);
        List<UserDetails> userDetailsList =
                userDetailsDao.getEntityListBySQLQuery("select * from user_details u;");
        Assertions.assertFalse(userDetailsList.isEmpty());


    }

    //category
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void categoryDaoBeanTest() {
        ICategoryDao<Category> categoryDao = context.getBean(ICategoryDao.class);
        Category category = new Category();
        category.setCategoryEnum(CategoryEnum.BEAUTY_AND_HEALTH);
        categoryDao.saveEntity(category);
        List<Category> categoryList = categoryDao.getEntityListBySQLQuery("select * from category c;");
        Assertions.assertFalse(categoryList.isEmpty());
        Assertions.assertEquals(CategoryEnum.BEAUTY_AND_HEALTH.getValue(), categoryList.get(0).getCategoryName());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void brandDaoBeanTest() {
        IBrandDao<Brand> brandDao = context.getBean(IBrandDao.class);
        Brand brand = new Brand();
        brand.setBrandEnum(BrandEnum.APPLE);
        brandDao.saveEntity(brand);
        List<Brand> brandList = brandDao.getEntityListBySQLQuery("select * from brand b;");
        Assertions.assertFalse(brandList.isEmpty());
        Assertions.assertEquals(BrandEnum.APPLE.getValue(), brandList.get(0).getName());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void discountDaoBeanTest() {
        IDiscountDao<Discount> discountDao = context.getBean(IDiscountDao.class);
        Discount discount = new Discount();
        discountDao.saveEntity(discount);
        List<Discount> userRoleList = discountDao.getEntityListBySQLQuery("select * from discount d;");
        Assertions.assertFalse(userRoleList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void productTypeDaoBeanTest() {
        IProductTypeDao<ProductType> productTypeDao = context.getBean(IProductTypeDao.class);
        ProductType productType = new ProductType();
        productType.setProductType(ProductTypeEnum.CLAUSE);
        productTypeDao.saveEntity(productType);
        List<ProductType> productTypeList = productTypeDao.getEntityListBySQLQuery("select * from product_type p;");
        Assertions.assertFalse(productTypeList.isEmpty());
        Assertions.assertEquals(ProductTypeEnum.CLAUSE.getValue(), productTypeList.get(0).getName());

    }
}
