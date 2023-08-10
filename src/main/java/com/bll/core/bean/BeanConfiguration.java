package com.bll.core.bean;

import com.core.im.modal.bucket.Bucket;
import com.core.im.modal.liked.Liked;
import com.core.im.modal.option.Option;
import com.core.im.modal.option.OptionGroup;
import com.core.im.modal.order.OrderAddress;
import com.core.im.modal.order.OrderDetail;
import com.core.im.modal.order.OrderHistory;
import com.core.im.modal.order.OrderItem;
import com.core.im.modal.order.OrderStatus;
import com.core.im.modal.post.Comment;
import com.core.im.modal.post.Post;
import com.core.im.modal.product.*;
import com.core.im.modal.review.Review;
import com.core.im.modal.shop.Shop;
import com.core.im.modal.shop.ShopAddress;
import com.core.im.modal.store.Store;
import com.core.im.modal.store.StoreAddress;
import com.core.im.modal.user.AppUser;
import com.core.im.modal.user.UserAddress;
import com.core.im.modal.user.UserDetail;
import com.core.im.modal.user.UserPayment;
import com.core.im.modal.user.UserRole;
import com.core.im.modal.viewed.Viewed;
import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.config.factory.IConfigurationSessionFactory;
import com.cos.core.dao.basic.*;
import com.cos.core.dao.bucket.IBucketDao;
import com.cos.core.dao.liked.ILikedDao;
import com.cos.core.dao.option.IOptionDao;
import com.cos.core.dao.option.IOptionGroupDao;
import com.cos.core.dao.order.IOrderAddressDao;
import com.cos.core.dao.order.IOrderDetailDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import com.cos.core.dao.order.IOrderItemDao;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.post.ICommentDao;
import com.cos.core.dao.post.IPostDao;
import com.cos.core.dao.product.*;
import com.cos.core.dao.rating.IRatingDao;
import com.cos.core.dao.review.IReviewDao;
import com.cos.core.dao.shop.IShopAddressDao;
import com.cos.core.dao.shop.IShopDao;
import com.cos.core.dao.store.IStoreAddressDao;
import com.cos.core.dao.store.IStoreDao;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserAddressDao;
import com.cos.core.dao.user.IUserDetailDao;
import com.cos.core.dao.user.IUserPaymentDao;
import com.cos.core.dao.user.IUserRoleDao;
import com.cos.core.dao.viewed.IViewedDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
               ConnectionPoolType.HIKARI, ConfigDbType.XML
        );
        return configurationSessionFactory.getSessionFactory();
    }

    //dto bean example
//    @Bean
//    public IDtoEntityDao dtoEntityDao(SessionFactory sessionFactory) {
//        IDtoEntityDao dtoEntityDao = new BasicDtoEntityDao(sessionFactory);
//        dtoEntityDao.setClazz();
//        return dtoEntityDao;
//    }

    @Bean
    public IAppUserDao<AppUser> appUserDao(SessionFactory sessionFactory) {
        IAppUserDao<AppUser> appUserDao = new BasicAppUserDao<>(sessionFactory);
        appUserDao.setClazz(AppUser.class);
        return appUserDao;
    }

    @Bean
    public IUserRoleDao<UserRole> userRoleDao(SessionFactory sessionFactory) {
        IUserRoleDao<UserRole> userRoleDao = new BasicUserRoleDao<>(sessionFactory);
        userRoleDao.setClazz(UserRole.class);
        return userRoleDao;
    }

    @Bean
    public IUserDetailDao<UserDetail> userDetailsDao(SessionFactory sessionFactory) {
        IUserDetailDao<UserDetail> userDetailsDao = new BasicUserDetailDao<>(sessionFactory);
        userDetailsDao.setClazz(UserDetail.class);
        return userDetailsDao;
    }

    @Bean
    public IUserPaymentDao<UserPayment> userPaymentDao(SessionFactory sessionFactory) {
        IUserPaymentDao<UserPayment> userPaymentDao = new BasicUserPaymentDao<>(sessionFactory);
        userPaymentDao.setClazz(UserPayment.class);
        return userPaymentDao;
    }

    @Bean
    public IUserAddressDao<UserAddress> userAddressDao(SessionFactory sessionFactory) {
        IUserAddressDao<UserAddress> userAddressDao = new BasicUserAddressDao<>(sessionFactory);
        userAddressDao.setClazz(UserAddress.class);
        return userAddressDao;
    }

    @Bean
    public IOrderItemDao<OrderItem> orderItemDao(SessionFactory sessionFactory) {
        IOrderItemDao<OrderItem> orderItemDao = new BasicOrderItemDao<>(sessionFactory);
        orderItemDao.setClazz(OrderItem.class);
        return orderItemDao;
    }

    @Bean
    public IOrderStatusDao<OrderStatus> orderStatusDao(SessionFactory sessionFactory) {
        IOrderStatusDao<OrderStatus> orderStatusDao = new BasicOrderStatusDao<>(sessionFactory);
        orderStatusDao.setClazz(OrderStatus.class);
        return orderStatusDao;
    }

    @Bean
    public IOrderDetailDao<OrderDetail> orderDetailDao(SessionFactory sessionFactory) {
        IOrderDetailDao<OrderDetail> orderDetailDao = new BasicOrderDetailDao<>(sessionFactory);
        orderDetailDao.setClazz(OrderDetail.class);
        return orderDetailDao;
    }

    @Bean
    public IOrderAddressDao<OrderAddress> orderAddressDao(SessionFactory sessionFactory) {
        IOrderAddressDao<OrderAddress> orderAddressDao = new BasicOrderAddressDao<>(sessionFactory);
        orderAddressDao.setClazz(OrderAddress.class);
        return orderAddressDao;
    }

    @Bean
    public IOrderHistoryDao<OrderHistory> orderHistoryDao(SessionFactory sessionFactory) {
        IOrderHistoryDao<OrderHistory> orderHistoryDao = new BasicOrderHistoryDao<>(sessionFactory);
        orderHistoryDao.setClazz(OrderHistory.class);
        return orderHistoryDao;
    }

    @Bean
    public IProductTypeDao<ProductType> productTypeDao(SessionFactory sessionFactory) {
        IProductTypeDao<ProductType> productTypeDao = new BasicProductTypeDao<>(sessionFactory);
        productTypeDao.setClazz(ProductType.class);
        return productTypeDao;
    }

    @Bean
    public IDiscountDao<Discount> discountDao(SessionFactory sessionFactory) {
        IDiscountDao<Discount> discountDao = new BasicDiscountDao<>(sessionFactory);
        discountDao.setClazz(Discount.class);
        return discountDao;
    }

    @Bean
    public IBrandDao<Brand> brandDao(SessionFactory sessionFactory) {
        IBrandDao<Brand> brandDao = new BasicBrandDao<>(sessionFactory);
        brandDao.setClazz(Brand.class);
        return brandDao;
    }

    @Bean
    public ICategoryDao<Category> categoryDao(SessionFactory sessionFactory) {
        ICategoryDao<Category> categoryDao = new BasicCategoryDao<>(sessionFactory);
        categoryDao.setClazz(Category.class);
        return categoryDao;
    }

    @Bean
    public IProductStatusDao<ProductStatus> productStatusDao(SessionFactory sessionFactory) {
        IProductStatusDao<ProductStatus> productStatusDao = new BasicProductStatusDao<>(sessionFactory);
        productStatusDao.setClazz(ProductStatus.class);
        return productStatusDao;
    }

    @Bean
    public ICommentDao<Comment> commentDao(SessionFactory sessionFactory) {
        ICommentDao<Comment> commentDao = new BasicCommentDao<>(sessionFactory);
        commentDao.setClazz(Comment.class);
        return commentDao;
    }

    @Bean
    public IPostDao<Post> postDao(SessionFactory sessionFactory) {
        IPostDao<Post> postDao = new BasicPostDao<>(sessionFactory);
        postDao.setClazz(Post.class);
        return postDao;
    }

    @Bean
    public IShopAddressDao<ShopAddress> shopAddressDao(SessionFactory sessionFactory) {
        IShopAddressDao<ShopAddress> shopAddressDao = new BasicShopAddressDao<>(sessionFactory);
        shopAddressDao.setClazz(ShopAddress.class);
        return shopAddressDao;
    }

    @Bean
    public IStoreAddressDao<StoreAddress> storeAddressDao(SessionFactory sessionFactory) {
        IStoreAddressDao<StoreAddress> storeAddressDao = new BasicStoreAddressDao<>(sessionFactory);
        storeAddressDao.setClazz(StoreAddress.class);
        return storeAddressDao;
    }

    @Bean
    public IShopDao<Shop> shopDao(SessionFactory sessionFactory) {
        IShopDao<Shop> shopDao = new BasicShopDao<>(sessionFactory);
        shopDao.setClazz(Shop.class);
        return shopDao;
    }

    @Bean
    public IStoreDao<Store> storeDao(SessionFactory sessionFactory) {
        IStoreDao<Store> storeDao = new BasicStoreDao<>(sessionFactory);
        storeDao.setClazz(Store.class);
        return storeDao;
    }

    @Bean
    public IOptionGroupDao<OptionGroup> optionGroupDao(SessionFactory sessionFactory) {
        IOptionGroupDao<OptionGroup> optionGroupDao = new BasicOptionGroupDao<>(sessionFactory);
        optionGroupDao.setClazz(OptionGroup.class);
        return optionGroupDao;
    }

    @Bean
    public IOptionDao<Option> optionDao(SessionFactory sessionFactory) {
        IOptionDao<Option> optionDao = new BasicOptionDao<>(sessionFactory);
        optionDao.setClazz(Option.class);
        return optionDao;
    }

    @Bean
    public IBucketDao<Bucket> bucketDao(SessionFactory sessionFactory) {
        IBucketDao<Bucket> bucketDao = new BasicBucketDao<>(sessionFactory);
        bucketDao.setClazz(Bucket.class);
        return bucketDao;
    }

    @Bean
    public ILikedDao<Liked> likedDao(SessionFactory sessionFactory) {
        ILikedDao<Liked> likedDao = new BasicLikedDao<>(sessionFactory);
        likedDao.setClazz(Liked.class);
        return likedDao;
    }

    @Bean
    public IViewedDao<Viewed> viewedDao(SessionFactory sessionFactory) {
        IViewedDao<Viewed> viewedDao = new BasicViewedDao<>(sessionFactory);
        viewedDao.setClazz(Viewed.class);
        return viewedDao;
    }

    @Bean
    public IProductDao<Product> productDao(SessionFactory sessionFactory) {
        IProductDao<Product> productDao = new BasicProductDao<>(sessionFactory);
        productDao.setClazz(Product.class);
        return productDao;
    }

    @Bean
    public IReviewDao<Review> reviewDao(SessionFactory sessionFactory) {
        IReviewDao<Review> reviewDao = new BasicReviewDao<>(sessionFactory);
        reviewDao.setClazz(Review.class);
        return reviewDao;
    }

    @Bean
    public IRatingDao<Rating> ratingDao(SessionFactory sessionFactory) {
        IRatingDao<Rating> ratingDao = new BasicRatingDao<>(sessionFactory);
        ratingDao.setClazz(Rating.class);
        return ratingDao;
    }
}
