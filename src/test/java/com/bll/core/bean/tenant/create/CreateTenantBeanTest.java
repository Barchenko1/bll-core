package com.bll.core.bean.tenant.create;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
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
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.List;

import static com.bll.core.mapper.TestConstant.CART_NUMBER;
import static com.bll.core.mapper.TestConstant.DATE_OF_CREATE;
import static com.bll.core.mapper.TestConstant.EMAIL;
import static com.bll.core.mapper.TestConstant.NAME;
import static com.bll.core.mapper.TestConstant.PASSWORD;
import static com.bll.core.mapper.TestConstant.SOME_STRING;

@ExtendWith(DBUnitExtension.class)
public class CreateTenantBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;

        System.out.println("//////////////////////////////");
        System.out.println(context.getBeanDefinitionNames().length);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        System.out.println("//////////////////////////////");
    }

    //user beans
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserSet.xml")
    void appUserDaoBeanTest() {
        IAppUserDao appUserDao = context.getBean(IAppUserDao.class);
        AppUser appUser = new AppUser();
        appUser.setEmail(EMAIL);
        appUser.setUsername(NAME);
        appUser.setPassword(PASSWORD);
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
        userAddress.setStreet(SOME_STRING);
        userAddressDao.saveEntity(userAddress);
        List<UserAddress> userAddressList = userAddressDao.getEntityListBySQLQuery("select * from user_address a;");
        Assertions.assertFalse(userAddressList.isEmpty());
        Assertions.assertEquals(SOME_STRING, userAddressList.get(0).getStreet());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserDetailSet.xml")
    void userDetailDaoBeanTest() {
        IUserDetailDao userDetailDao = context.getBean(IUserDetailDao.class);
        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(NAME);
        userDetailDao.saveEntity(userDetail);
        List<UserDetail> userDetailList =
                userDetailDao.getEntityListBySQLQuery("select * from user_detail u;");
        Assertions.assertFalse(userDetailList.isEmpty());
        Assertions.assertEquals(NAME, userDetailList.get(0).getFirstName());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/user/createExpectedUserPaymentSet.xml")
    void userPaymentDaoBeanTest() {
        IUserPaymentDao userPaymentDao = context.getBean(IUserPaymentDao.class);
        UserPayment userPayment = new UserPayment();
        userPayment.setCartNumber(CART_NUMBER);
        userPaymentDao.saveEntity(userPayment);
        List<UserPayment> userPaymentList =
                userPaymentDao.getEntityListBySQLQuery("select * from user_payment u;");
        Assertions.assertFalse(userPaymentList.isEmpty());
        Assertions.assertEquals(CART_NUMBER, userPaymentList.get(0).getCartNumber());
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

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/product/createExpectedProductSet.xml")
    void productDaoBeanTest() {
        IProductDao productDao = context.getBean(IProductDao.class);
        Product product = new Product();
        product.setName(NAME);
        product.setDateOfCreate(DATE_OF_CREATE);
        productDao.saveEntity(product);
        List<Viewed> viewedList =
                productDao.getEntityListBySQLQuery("select * from product p;");
        Assertions.assertFalse(viewedList.isEmpty());

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
        orderAddress.setStreet(SOME_STRING);
        orderAddressDao.saveEntity(orderAddress);
        List<OrderAddress> orderAddressList =
                orderAddressDao.getEntityListBySQLQuery("select * from order_address o;");
        Assertions.assertFalse(orderAddressList.isEmpty());
        Assertions.assertEquals(SOME_STRING, orderAddressList.get(0).getStreet());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/order/createExpectedOrderDetailSet.xml")
    void orderDetailDaoBeanTest() {
        IOrderDetailDao orderDetailDao = context.getBean(IOrderDetailDao.class);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setFirstName(NAME);
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
    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/order/createExpectedOrderItemSet.xml")
    void orderItemDaoBeanTest() {
        IOrderItemDao orderDao = context.getBean(IOrderItemDao.class);
        OrderItem orderItem = new OrderItem();
        orderItem.setDateOfCreate(DATE_OF_CREATE);
        orderDao.saveEntity(orderItem);
        List<OrderItem> orderItemList = orderDao.getEntityListBySQLQuery("select * from order_item o;");
        Assertions.assertFalse(orderItemList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/order/createExpectedOrderHistorySet.xml")
    void orderHistoryDaoBeanTest() {
        IOrderHistoryDao orderHistoryDao = context.getBean(IOrderHistoryDao.class);
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setDateOfCreate(DATE_OF_CREATE);
        orderHistoryDao.saveEntity(orderHistory);
        List<OrderHistory> orderHistoryList =
                orderHistoryDao.getEntityListBySQLQuery("select * from order_history o;");
        Assertions.assertFalse(orderHistoryList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/business/createExpectedBusinessAddressSet.xml")
    void businessAddressDaoBeanTest() {
        IBusinessAddressDao businessAddressDao = context.getBean(IBusinessAddressDao.class);
        BusinessAddress businessAddress = new BusinessAddress();
        businessAddress.setApartmentNumber(1);
        businessAddress.setBuilding(1);
        businessAddress.setFlor(1);
        businessAddress.setStreet(SOME_STRING);
        businessAddressDao.saveEntity(businessAddress);
        List<BusinessAddress> businessAddressList =
                businessAddressDao.getEntityListBySQLQuery("select * from business_address b;");
        Assertions.assertFalse(businessAddressList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/business/createExpectedShopSet.xml")
    void shopDaoBeanTest() {
        IShopDao shopDao = context.getBean(IShopDao.class);
        Shop shop = new Shop();

        shop.setName(NAME);
        shopDao.saveEntity(shop);
        List<Shop> shopList =
                shopDao.getEntityListBySQLQuery("select * from shop s;");
        Assertions.assertFalse(shopList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/business/createExpectedStoreSet.xml")
    void storeDaoBeanTest() {
        IStoreDao storeDao = context.getBean(IStoreDao.class);
        Store store = new Store();

        store.setName(NAME);
        storeDao.saveEntity(store);
        List<Store> shopList =
                storeDao.getEntityListBySQLQuery("select * from store s;");
        Assertions.assertFalse(shopList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/post/createExpectedPostSet.xml")
    void postDaoBeanTest() {
        IPostDao postDao = context.getBean(IPostDao.class);
        Post post = new Post();
        post.setAuthorName(NAME);
        post.setAuthorEmail(EMAIL);
        postDao.saveEntity(post);
        List<Post> postList =
                postDao.getEntityListBySQLQuery("select * from post s;");
        Assertions.assertFalse(postList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/rating/createExpectedRatingSet.xml")
    void ratingDaoBeanTest() {
        IRatingDao ratingDao = context.getBean(IRatingDao.class);
        Rating rating = new Rating();
        rating.setRatingEnum(RateEnum.FIVE);
        ratingDao.saveEntity(rating);
        List<Rating> ratingList =
                ratingDao.getEntityListBySQLQuery("select * from rating r;");
        Assertions.assertFalse(ratingList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/liked/createExpectedLikedSet.xml")
    void likedDaoBeanTest() {
        ILikedDao likedDao = context.getBean(ILikedDao.class);
        Liked liked = new Liked();
        liked.setDateOfCreate(DATE_OF_CREATE);
        likedDao.saveEntity(liked);
        List<Liked> ratingList =
                likedDao.getEntityListBySQLQuery("select * from liked l;");
        Assertions.assertFalse(ratingList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/review/createExpectedReviewSet.xml")
    void reviewDaoBeanTest() {
        IReviewDao reviewDao = context.getBean(IReviewDao.class);
        Review review = new Review();
        review.setDateOfCreate(DATE_OF_CREATE);
        reviewDao.saveEntity(review);
        List<Review> reviewList =
                reviewDao.getEntityListBySQLQuery("select * from review r;");
        Assertions.assertFalse(reviewList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/viewed/createExpectedViewedSet.xml")
    void viewedDaoBeanTest() {
        IViewedDao viewedDao = context.getBean(IViewedDao.class);
        Viewed viewed = new Viewed();
        viewed.setDateOfCreate(DATE_OF_CREATE);
        viewedDao.saveEntity(viewed);
        List<Viewed> viewedList =
                viewedDao.getEntityListBySQLQuery("select * from viewed v;");
        Assertions.assertFalse(viewedList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/bucket/createExpectedBucketSet.xml")
    void bucketDaoBeanTest() {
        IBucketDao bucketDao = context.getBean(IBucketDao.class);
        Bucket bucket = new Bucket();
        bucket.setDateOfLastUpdate(DATE_OF_CREATE);
        bucketDao.saveEntity(bucket);
        List<Bucket> bucketList =
                bucketDao.getEntityListBySQLQuery("select * from bucket b;");
        Assertions.assertFalse(bucketList.isEmpty());

    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    @ExpectedDataSet("/data/expected/create/option/createExpectedOptionGroupSet.xml")
    void optionGroupDaoBeanTest() {
        IOptionGroupDao optionGroupDao = context.getBean(IOptionGroupDao.class);
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.setOptionGroup(OptionGroupEnum.COLOR);
        optionGroupDao.saveEntity(optionGroup);
        List<OptionGroup> optionGroupList =
                optionGroupDao.getEntityListBySQLQuery("select * from option_group o;");
        Assertions.assertFalse(optionGroupList.isEmpty());
    }

//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true)
//    @ExpectedDataSet("/data/expected/create/option/updatedExpectedProductSet.xml")
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
