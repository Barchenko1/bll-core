package com.bll.core.bean.tenant.update;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.bll.core.util.TestUtil;
import com.core.im.org.modal.Organization;
import com.core.im.tenant.constant.BrandEnum;
import com.core.im.tenant.constant.CategoryEnum;
import com.core.im.tenant.constant.OptionGroupEnum;
import com.core.im.tenant.constant.OrderStatusEnum;
import com.core.im.tenant.constant.ProductStatusEnum;
import com.core.im.tenant.constant.ProductTypeEnum;
import com.core.im.tenant.constant.RateEnum;
import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.modal.bucket.Bucket;
import com.core.im.tenant.modal.business.BusinessAddress;
import com.core.im.tenant.modal.business.Shop;
import com.core.im.tenant.modal.business.Store;
import com.core.im.tenant.modal.liked.Liked;
import com.core.im.tenant.modal.option.OptionGroup;
import com.core.im.tenant.modal.order.OrderAddress;
import com.core.im.tenant.modal.order.OrderDetail;
import com.core.im.tenant.modal.order.OrderHistory;
import com.core.im.tenant.modal.order.OrderItem;
import com.core.im.tenant.modal.order.OrderStatus;
import com.core.im.tenant.modal.post.Post;
import com.core.im.tenant.modal.product.Brand;
import com.core.im.tenant.modal.product.Category;
import com.core.im.tenant.modal.product.Discount;
import com.core.im.tenant.modal.product.Product;
import com.core.im.tenant.modal.product.ProductStatus;
import com.core.im.tenant.modal.product.ProductType;
import com.core.im.tenant.modal.product.Rating;
import com.core.im.tenant.modal.review.Review;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserAddress;
import com.core.im.tenant.modal.user.UserDetail;
import com.core.im.tenant.modal.user.UserPayment;
import com.core.im.tenant.modal.user.UserRole;
import com.core.im.tenant.modal.viewed.Viewed;
import com.cos.core.dao.bucket.IBucketDao;
import com.cos.core.dao.business.IBusinessAddressDao;
import com.cos.core.dao.business.IShopDao;
import com.cos.core.dao.business.IStoreDao;
import com.cos.core.dao.liked.ILikedDao;
import com.cos.core.dao.option.IOptionGroupDao;
import com.cos.core.dao.order.IOrderAddressDao;
import com.cos.core.dao.order.IOrderDetailDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import com.cos.core.dao.order.IOrderItemDao;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.post.IPostDao;
import com.cos.core.dao.product.IBrandDao;
import com.cos.core.dao.product.ICategoryDao;
import com.cos.core.dao.product.IDiscountDao;
import com.cos.core.dao.product.IProductDao;
import com.cos.core.dao.product.IProductStatusDao;
import com.cos.core.dao.product.IProductTypeDao;
import com.cos.core.dao.rating.IRatingDao;
import com.cos.core.dao.review.IReviewDao;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserAddressDao;
import com.cos.core.dao.user.IUserDetailDao;
import com.cos.core.dao.user.IUserPaymentDao;
import com.cos.core.dao.user.IUserRoleDao;
import com.cos.core.dao.viewed.IViewedDao;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import static com.bll.core.util.TestConstant.CART_NUMBER;
import static com.bll.core.util.TestConstant.CART_NUMBER_UPDATED;
import static com.bll.core.util.TestConstant.DATE_OF_CREATE;
import static com.bll.core.util.TestConstant.EMAIL;
import static com.bll.core.util.TestConstant.EMAIL_UPDATED;
import static com.bll.core.util.TestConstant.NAME;
import static com.bll.core.util.TestConstant.NAME_UPDATED;
import static com.bll.core.util.TestConstant.PASSWORD;
import static com.bll.core.util.TestConstant.PASSWORD_UPDATED;
import static com.bll.core.util.TestConstant.SOME_STRING;
import static com.bll.core.util.TestConstant.DATE_OF_CREATE_UPDATED;
import static com.bll.core.util.TestConstant.SOME_STRING_UPDATED;

@ExtendWith(DBUnitExtension.class)
public class UpdateTenantBeanTest extends AbstractBeanTest {
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
    @ExpectedDataSet("/data/expected/update/user/updatedExpectedUserSet.xml")
    void appUserDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/user/appUserDataSet.xml");

        IAppUserDao appUserDao = context.getBean(IAppUserDao.class);
        AppUser appUser =
                (AppUser) getEntity(appUserDao, "app_user", "username", NAME);

        appUser.setEmail(EMAIL_UPDATED);
        appUser.setUsername(NAME_UPDATED);
        appUser.setPassword(PASSWORD_UPDATED);
        appUserDao.updateEntity(appUser);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/user/updatedExpectedUserRoleSet.xml")
    void userRoleDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/user/userRoleDataSet.xml");


        IUserRoleDao userRoleDao = context.getBean(IUserRoleDao.class);
        UserRole userRole =
                (UserRole) getEntity(userRoleDao, "user_role", "name", "User");
        userRole.setRoleEnum(RoleEnum.ROLE_ADMIN);
        userRoleDao.updateEntity(userRole);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/user/updatedExpectedUserAddressSet.xml")
    void userAddressBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/user/userAddressDataSet.xml");

        IUserAddressDao userAddressDao = context.getBean(IUserAddressDao.class);
        UserAddress userAddress =
                (UserAddress) getEntity(userAddressDao, "user_address", "street", SOME_STRING);
        userAddress.setFlor(9);
        userAddress.setBuilding(1);
        userAddress.setApartmentNumber(111);
        userAddress.setStreet(SOME_STRING_UPDATED);
        userAddressDao.updateEntity(userAddress);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/user/updatedExpectedUserDetailSet.xml")
    void userDetailDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/user/userDetailDataSet.xml");

        IUserDetailDao userDetailDao = context.getBean(IUserDetailDao.class);
        UserDetail userDetail =
                (UserDetail) getEntity(userDetailDao, "user_detail", "firstName", NAME);
        userDetail.setFirstName(SOME_STRING_UPDATED);
        userDetailDao.updateEntity(userDetail);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/user/updatedExpectedUserPaymentSet.xml")
    void userPaymentDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/user/userPaymentDataSet.xml");

        IUserPaymentDao userPaymentDao = context.getBean(IUserPaymentDao.class);
        UserPayment userPayment =
                (UserPayment) getEntity(userPaymentDao, "user_payment", "cartnumber", CART_NUMBER);

        userPayment.setCartNumber(CART_NUMBER_UPDATED);
        userPaymentDao.updateEntity(userPayment);
    }

    //product beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/product/updatedExpectedCategorySet.xml")
    void categoryDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/product/categoryDataSet.xml");

        ICategoryDao categoryDao = context.getBean(ICategoryDao.class);
        Category category =
                (Category) getEntity(categoryDao, "category", "name", CategoryEnum.BEAUTY_AND_HEALTH.getValue());

        category.setCategoryEnum(CategoryEnum.APPLIANCES);
        categoryDao.updateEntity(category);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/product/updatedExpectedBrandSet.xml")
    void brandDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/product/brandDataSet.xml");

        IBrandDao brandDao = context.getBean(IBrandDao.class);
        Brand brand =
                (Brand) getEntity(brandDao, "brand", "name", BrandEnum.APPLE.getValue());

        brand.setBrandEnum(BrandEnum.LENOVO);
        brandDao.updateEntity(brand);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/product/updatedExpectedDiscountSet.xml")
    void discountDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/product/discountDataSet.xml");

        IDiscountDao discountDao = context.getBean(IDiscountDao.class);
        Discount discount =
                (Discount) getEntity(discountDao, "discount", "amount", 10L);

        discount.setAmount(11);
        discountDao.updateEntity(discount);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/product/updatedExpectedProductTypeSet.xml")
    void productTypeDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/product/productTypeDataSet.xml");

        IProductTypeDao productTypeDao = context.getBean(IProductTypeDao.class);
        ProductType productType =
                (ProductType) getEntity(productTypeDao, "product_type", "name", ProductTypeEnum.CLAUSE.getValue());

        productType.setProductType(ProductTypeEnum.TECHNICS);
        productTypeDao.updateEntity(productType);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/product/updatedExpectedProductStatusSet.xml")
    void productStatusDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/product/productStatusDataSet.xml");

        IProductStatusDao productStatusDao = context.getBean(IProductStatusDao.class);
        ProductStatus productStatus =
                (ProductStatus) getEntity(productStatusDao, "product_status", "name", ProductStatusEnum.NEW.name());

        productStatus.setName(ProductStatusEnum.RUNNING_OUT);
        productStatusDao.updateEntity(productStatus);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/product/updatedExpectedProductSet.xml")
    void productDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/product/productDataSet.xml");

        IProductDao productDao = context.getBean(IProductDao.class);
        Product product =
                (Product) getEntity(productDao, "product", "name", NAME);

        product.setName(NAME_UPDATED);
        product.setDateOfCreate(DATE_OF_CREATE_UPDATED);
        productDao.updateEntity(product);

    }

    //order beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/order/updatedExpectedOrderAddressSet.xml")
    void orderAddressDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/order/orderAddressDataSet.xml");

        IOrderAddressDao orderAddressDao = context.getBean(IOrderAddressDao.class);
        OrderAddress orderAddress =
                (OrderAddress) getEntity(orderAddressDao, "order_address", "street", SOME_STRING);

        orderAddress.setApartmentNumber(1);
        orderAddress.setBuilding(1);
        orderAddress.setFlor(1);
        orderAddress.setStreet(SOME_STRING_UPDATED);
        orderAddressDao.updateEntity(orderAddress);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/order/updatedExpectedOrderDetailSet.xml")
    void orderDetailDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/order/orderDetailDataSet.xml");

        IOrderDetailDao orderDetailDao = context.getBean(IOrderDetailDao.class);
        OrderDetail orderDetail =
                (OrderDetail) getEntity(orderDetailDao, "order_detail", "firstName", NAME);

        orderDetail.setFirstName(NAME_UPDATED);
        orderDetailDao.updateEntity(orderDetail);

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/order/updatedExpectedOrderStatusSet.xml")
    void orderStatusDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/order/orderStatusDataSet.xml");

        IOrderStatusDao orderStatusDao = context.getBean(IOrderStatusDao.class);
        OrderStatus status =
                (OrderStatus) getEntity(orderStatusDao, "order_status", "name", OrderStatusEnum.NEW.name());

        status.setName(OrderStatusEnum.COMPLETE);
        orderStatusDao.updateEntity(status);
    }

    //to do
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/order/updatedExpectedOrderItemSet.xml")
    void orderItemDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/order/orderItemDataSet.xml");

        IOrderItemDao orderDao = context.getBean(IOrderItemDao.class);
        OrderItem orderItem =
                (OrderItem) getEntity(orderDao, "order_item", "dateofcreate", DATE_OF_CREATE);

        orderItem.setDateOfCreate(DATE_OF_CREATE_UPDATED);
        orderDao.updateEntity(orderItem);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/order/updatedExpectedOrderHistorySet.xml")
    void orderHistoryDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/order/orderHistoryDataSet.xml");

        IOrderHistoryDao orderHistoryDao = context.getBean(IOrderHistoryDao.class);
        OrderHistory orderHistory =
                (OrderHistory) getEntity(orderHistoryDao, "order_history", "dateofcreate", DATE_OF_CREATE);

        orderHistory.setDateOfCreate(DATE_OF_CREATE_UPDATED);
        orderHistoryDao.updateEntity(orderHistory);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/business/updatedExpectedBusinessAddressSet.xml")
    void businessAddressDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/business/businessAddressDataSet.xml");

        IBusinessAddressDao businessAddressDao = context.getBean(IBusinessAddressDao.class);
        BusinessAddress businessAddress =
                (BusinessAddress) getEntity(businessAddressDao, "business_address", "street", SOME_STRING);

        businessAddress.setApartmentNumber(1);
        businessAddress.setBuilding(1);
        businessAddress.setFlor(1);
        businessAddress.setStreet(SOME_STRING_UPDATED);
        businessAddressDao.updateEntity(businessAddress);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/business/updatedExpectedShopSet.xml")
    void shopDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/business/shopDataSet.xml");

        IShopDao shopDao = context.getBean(IShopDao.class);
        Shop shop =
                (Shop) getEntity(shopDao, "shop", "name", NAME);

        shop.setName(NAME_UPDATED);
        shopDao.updateEntity(shop);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/business/updatedExpectedStoreSet.xml")
    void storeDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/business/storeDataSet.xml");

        IStoreDao storeDao = context.getBean(IStoreDao.class);
        Store store =
                (Store) getEntity(storeDao, "store", "name", NAME);

        store.setName(NAME_UPDATED);
        storeDao.updateEntity(store);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/post/updatedExpectedPostSet.xml")
    void postDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/post/postDataSet.xml");

        IPostDao postDao = context.getBean(IPostDao.class);
        Post post =
                (Post) getEntity(postDao, "post", "authorName", NAME);

        post.setAuthorName(NAME_UPDATED);
        post.setAuthorEmail(EMAIL_UPDATED);
        postDao.updateEntity(post);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/rating/updatedExpectedRatingSet.xml")
    void ratingDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/rating/ratingDataSet.xml");

        IRatingDao ratingDao = context.getBean(IRatingDao.class);
        Rating rating =
                (Rating) getEntity(ratingDao, "rating", "value", RateEnum.FIVE.getValue());

        rating.setRatingEnum(RateEnum.ONE);
        ratingDao.updateEntity(rating);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/liked/updatedExpectedLikedSet.xml")
    void likedDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/liked/likedDataSet.xml");

        ILikedDao likedDao = context.getBean(ILikedDao.class);
        Liked liked =
                (Liked) getEntity(likedDao, "liked", "dateofcreate", DATE_OF_CREATE);

        liked.setDateOfCreate(DATE_OF_CREATE_UPDATED);
        likedDao.updateEntity(liked);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/review/updatedExpectedReviewSet.xml")
    void reviewDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/review/reviewDataSet.xml");

        IReviewDao reviewDao = context.getBean(IReviewDao.class);
        Review review =
                (Review) getEntity(reviewDao, "review", "dateofcreate", DATE_OF_CREATE);

        review.setDateOfCreate(DATE_OF_CREATE_UPDATED);
        reviewDao.updateEntity(review);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/viewed/updatedExpectedViewedSet.xml")
    void viewedDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/viewed/viewedDataSet.xml");

        IViewedDao viewedDao = context.getBean(IViewedDao.class);
        Viewed viewed =
                (Viewed) getEntity(viewedDao, "viewed", "dateofcreate", DATE_OF_CREATE);

        viewed.setDateOfCreate(DATE_OF_CREATE_UPDATED);
        viewedDao.updateEntity(viewed);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/bucket/updatedExpectedBucketSet.xml")
    void bucketDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/bucket/bucketDataSet.xml");

        IBucketDao bucketDao = context.getBean(IBucketDao.class);
        Bucket bucket =
                (Bucket) getEntity(bucketDao, "bucket", "dateoflastupdate", DATE_OF_CREATE);

        bucket.setDateOfLastUpdate(DATE_OF_CREATE_UPDATED);
        bucketDao.updateEntity(bucket);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/update/option/updatedExpectedOptionGroupSet.xml")
    void optionGroupDaoBeanTest() {
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT,
                "/data/initclientdb/option/optionGroupDataSet.xml");

        IOptionGroupDao optionGroupDao = context.getBean(IOptionGroupDao.class);
        OptionGroup optionGroup =
                (OptionGroup) getEntity(optionGroupDao, "option_group", "optionGroup", OptionGroupEnum.COLOR.name());

        optionGroup.setOptionGroup(OptionGroupEnum.SIZE);
        optionGroupDao.updateEntity(optionGroup);
    }

//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true)
//    @ExpectedDataSet("/data/expected/update/option/updatedExpectedProductSet.xml")
//    void optionDaoBeanTest() {
//        IOptionDao optionDao = context.getBean(IOptionDao.class);
//        Option option = new Option();
//        option.set
//        optionDao.saveEntity(option);
//        List<Option> optionList =
//                optionDao.getEntityListBySQLQuery("select * from option o;");
//        Assertions.assertFalse(optionList.isEmpty());
//    }

}
