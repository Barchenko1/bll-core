package com.bll.core.bean;

import com.core.im.modal.bucket.Bucket;
import com.core.im.modal.liked.Liked;
import com.core.im.modal.option.Option;
import com.core.im.modal.option.OptionGroup;
import com.core.im.modal.order.Order;
import com.core.im.modal.order.OrderAddress;
import com.core.im.modal.order.OrderDetails;
import com.core.im.modal.order.OrderHistory;
import com.core.im.modal.order.OrderStatus;
import com.core.im.modal.post.Comment;
import com.core.im.modal.post.Post;
import com.core.im.modal.product.Brand;
import com.core.im.modal.product.Category;
import com.core.im.modal.product.Discount;
import com.core.im.modal.product.Product;
import com.core.im.modal.product.ProductType;
import com.core.im.modal.product.Rating;
import com.core.im.modal.review.Review;
import com.core.im.modal.shop.Shop;
import com.core.im.modal.shop.ShopAddress;
import com.core.im.modal.store.Store;
import com.core.im.modal.store.StoreAddress;
import com.core.im.modal.user.AppUser;
import com.core.im.modal.user.UserAddress;
import com.core.im.modal.user.UserDetails;
import com.core.im.modal.user.UserPayment;
import com.core.im.modal.user.UserRole;
import com.core.im.modal.viewed.Viewed;
import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.config.factory.IConfigurationSessionFactory;
import com.cos.core.dao.bucket.IBucketDao;
import com.cos.core.dao.impl.DefaultAppUserDao;
import com.cos.core.dao.impl.DefaultBrandDao;
import com.cos.core.dao.impl.DefaultBucketDao;
import com.cos.core.dao.impl.DefaultCategoryDao;
import com.cos.core.dao.impl.DefaultCommentDao;
import com.cos.core.dao.impl.DefaultDiscountDao;
import com.cos.core.dao.impl.DefaultLikedDao;
import com.cos.core.dao.impl.DefaultOptionDao;
import com.cos.core.dao.impl.DefaultOptionGroupDao;
import com.cos.core.dao.impl.DefaultOrderAddressDao;
import com.cos.core.dao.impl.DefaultOrderDao;
import com.cos.core.dao.impl.DefaultOrderDetailsDao;
import com.cos.core.dao.impl.DefaultOrderHistoryDao;
import com.cos.core.dao.impl.DefaultOrderStatusDao;
import com.cos.core.dao.impl.DefaultPostDao;
import com.cos.core.dao.impl.DefaultProductDao;
import com.cos.core.dao.impl.DefaultProductTypeDao;
import com.cos.core.dao.impl.DefaultRatingDao;
import com.cos.core.dao.impl.DefaultReviewDao;
import com.cos.core.dao.impl.DefaultShopAddressDao;
import com.cos.core.dao.impl.DefaultShopDao;
import com.cos.core.dao.impl.DefaultStoreAddressDao;
import com.cos.core.dao.impl.DefaultStoreDao;
import com.cos.core.dao.impl.DefaultUserAddressDao;
import com.cos.core.dao.impl.DefaultUserDetailsDao;
import com.cos.core.dao.impl.DefaultUserPaymentDao;
import com.cos.core.dao.impl.DefaultUserRoleDao;
import com.cos.core.dao.impl.DefaultViewedDao;
import com.cos.core.dao.liked.ILikedDao;
import com.cos.core.dao.option.IOptionDao;
import com.cos.core.dao.option.IOptionGroupDao;
import com.cos.core.dao.order.IOrderAddressDao;
import com.cos.core.dao.order.IOrderDao;
import com.cos.core.dao.order.IOrderDetailsDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.post.ICommentDao;
import com.cos.core.dao.post.IPostDao;
import com.cos.core.dao.product.IBrandDao;
import com.cos.core.dao.product.ICategoryDao;
import com.cos.core.dao.product.IDiscountDao;
import com.cos.core.dao.product.IProductDao;
import com.cos.core.dao.product.IProductTypeDao;
import com.cos.core.dao.rating.IRatingDao;
import com.cos.core.dao.review.IReviewDao;
import com.cos.core.dao.shop.IShopAddressDao;
import com.cos.core.dao.shop.IShopDao;
import com.cos.core.dao.store.IStoreAddressDao;
import com.cos.core.dao.store.IStoreDao;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserAddressDao;
import com.cos.core.dao.user.IUserDetailsDao;
import com.cos.core.dao.user.IUserPaymentDao;
import com.cos.core.dao.user.IUserRoleDao;
import com.cos.core.dao.viewed.IViewedDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public SessionFactory sessionFactory() {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
               ConnectionPoolType.HIKARI, ConfigDbType.XML
        );
        return configurationSessionFactory.getSessionFactory();
    }

    @Bean
    public IAppUserDao<AppUser> appUserDao(SessionFactory sessionFactory) {
        IAppUserDao<AppUser> appUserDao = new DefaultAppUserDao<>(sessionFactory);
        appUserDao.setClazz(AppUser.class);
        return appUserDao;
    }

    @Bean
    public IUserRoleDao<UserRole> userRoleDao(SessionFactory sessionFactory) {
        IUserRoleDao<UserRole> userRoleDao = new DefaultUserRoleDao<>(sessionFactory);
        userRoleDao.setClazz(UserRole.class);
        return userRoleDao;
    }

    @Bean
    public IUserDetailsDao<UserDetails> userDetailsDao(SessionFactory sessionFactory) {
        IUserDetailsDao<UserDetails> userDetailsDao = new DefaultUserDetailsDao<>(sessionFactory);
        userDetailsDao.setClazz(UserDetails.class);
        return userDetailsDao;
    }

    @Bean
    public IUserPaymentDao<UserPayment> userPaymentDao(SessionFactory sessionFactory) {
        IUserPaymentDao<UserPayment> userPaymentDao = new DefaultUserPaymentDao<>(sessionFactory);
        userPaymentDao.setClazz(UserPayment.class);
        return userPaymentDao;
    }

    @Bean
    public IUserAddressDao<UserAddress> userAddressDao(SessionFactory sessionFactory) {
        IUserAddressDao<UserAddress> userAddressDao = new DefaultUserAddressDao<>(sessionFactory);
        userAddressDao.setClazz(UserAddress.class);
        return userAddressDao;
    }

    @Bean
    public IOrderDao<Order> orderDao(SessionFactory sessionFactory) {
        IOrderDao<Order> orderDao = new DefaultOrderDao<>(sessionFactory);
        orderDao.setClazz(Order.class);
        return orderDao;
    }

    @Bean
    public IOrderStatusDao<OrderStatus> orderStatusDao(SessionFactory sessionFactory) {
        IOrderStatusDao<OrderStatus> orderStatusDao = new DefaultOrderStatusDao<>(sessionFactory);
        orderStatusDao.setClazz(OrderStatus.class);
        return orderStatusDao;
    }

    @Bean
    public IOrderDetailsDao<OrderDetails> orderDetailsDao(SessionFactory sessionFactory) {
        IOrderDetailsDao<OrderDetails> orderDetailsDao = new DefaultOrderDetailsDao<>(sessionFactory);
        orderDetailsDao.setClazz(OrderDetails.class);
        return orderDetailsDao;
    }

    @Bean
    public IOrderAddressDao<OrderAddress> orderAddressDao(SessionFactory sessionFactory) {
        IOrderAddressDao<OrderAddress> orderAddressDao = new DefaultOrderAddressDao<>(sessionFactory);
        orderAddressDao.setClazz(OrderAddress.class);
        return orderAddressDao;
    }

    @Bean
    public IOrderHistoryDao<OrderHistory> orderHistoryDao(SessionFactory sessionFactory) {
        IOrderHistoryDao<OrderHistory> orderHistoryDao = new DefaultOrderHistoryDao<>(sessionFactory);
        orderHistoryDao.setClazz(OrderHistory.class);
        return orderHistoryDao;
    }

    @Bean
    public IProductTypeDao<ProductType> productTypeDao(SessionFactory sessionFactory) {
        IProductTypeDao<ProductType> productTypeDao = new DefaultProductTypeDao<>(sessionFactory);
        productTypeDao.setClazz(ProductType.class);
        return productTypeDao;
    }

    @Bean
    public IDiscountDao<Discount> discountDao(SessionFactory sessionFactory) {
        IDiscountDao<Discount> discountDao = new DefaultDiscountDao<>(sessionFactory);
        discountDao.setClazz(Discount.class);
        return discountDao;
    }

    @Bean
    public IBrandDao<Brand> brandDao(SessionFactory sessionFactory) {
        IBrandDao<Brand> brandDao = new DefaultBrandDao<>(sessionFactory);
        brandDao.setClazz(Brand.class);
        return brandDao;
    }

    @Bean
    public ICategoryDao<Category> categoryDao(SessionFactory sessionFactory) {
        ICategoryDao<Category> categoryDao = new DefaultCategoryDao<>(sessionFactory);
        categoryDao.setClazz(Category.class);
        return categoryDao;
    }

    @Bean
    public ICommentDao<Comment> commentDao(SessionFactory sessionFactory) {
        ICommentDao<Comment> commentDao = new DefaultCommentDao<>(sessionFactory);
        commentDao.setClazz(Comment.class);
        return commentDao;
    }

    @Bean
    public IPostDao<Post> postDao(SessionFactory sessionFactory) {
        IPostDao<Post> postDao = new DefaultPostDao<>(sessionFactory);
        postDao.setClazz(Post.class);
        return postDao;
    }

    @Bean
    public IShopAddressDao<ShopAddress> shopAddressDao(SessionFactory sessionFactory) {
        IShopAddressDao<ShopAddress> shopAddressDao = new DefaultShopAddressDao<>(sessionFactory);
        shopAddressDao.setClazz(ShopAddress.class);
        return shopAddressDao;
    }

    @Bean
    public IStoreAddressDao<StoreAddress> storeAddressDao(SessionFactory sessionFactory) {
        IStoreAddressDao<StoreAddress> storeAddressDao = new DefaultStoreAddressDao<>(sessionFactory);
        storeAddressDao.setClazz(StoreAddress.class);
        return storeAddressDao;
    }

    @Bean
    public IShopDao<Shop> shopDao(SessionFactory sessionFactory) {
        IShopDao<Shop> shopDao = new DefaultShopDao<>(sessionFactory);
        shopDao.setClazz(Shop.class);
        return shopDao;
    }

    @Bean
    public IStoreDao<Store> storeDao(SessionFactory sessionFactory) {
        IStoreDao<Store> storeDao = new DefaultStoreDao<>(sessionFactory);
        storeDao.setClazz(Store.class);
        return storeDao;
    }

    @Bean
    public IOptionGroupDao<OptionGroup> optionGroupDao(SessionFactory sessionFactory) {
        IOptionGroupDao<OptionGroup> optionGroupDao = new DefaultOptionGroupDao<>(sessionFactory);
        optionGroupDao.setClazz(OptionGroup.class);
        return optionGroupDao;
    }

    @Bean
    public IOptionDao<Option> optionDao(SessionFactory sessionFactory) {
        IOptionDao<Option> optionDao = new DefaultOptionDao<>(sessionFactory);
        optionDao.setClazz(Option.class);
        return optionDao;
    }

    @Bean
    public IBucketDao<Bucket> bucketDao(SessionFactory sessionFactory) {
        IBucketDao<Bucket> bucketDao = new DefaultBucketDao<>(sessionFactory);
        bucketDao.setClazz(Bucket.class);
        return bucketDao;
    }

    @Bean
    public ILikedDao<Liked> likedDao(SessionFactory sessionFactory) {
        ILikedDao<Liked> likedDao = new DefaultLikedDao<>(sessionFactory);
        likedDao.setClazz(Liked.class);
        return likedDao;
    }

    @Bean
    public IViewedDao<Viewed> viewedDao(SessionFactory sessionFactory) {
        IViewedDao<Viewed> viewedDao = new DefaultViewedDao<>(sessionFactory);
        viewedDao.setClazz(Viewed.class);
        return viewedDao;
    }

    @Bean
    public IProductDao<Product> productDao(SessionFactory sessionFactory) {
        IProductDao<Product> productDao = new DefaultProductDao<>(sessionFactory);
        productDao.setClazz(Product.class);
        return productDao;
    }

    @Bean
    public IReviewDao<Review> reviewDao(SessionFactory sessionFactory) {
        IReviewDao<Review> reviewDao = new DefaultReviewDao<>(sessionFactory);
        reviewDao.setClazz(Review.class);
        return reviewDao;
    }

    @Bean
    public IRatingDao<Rating> ratingDao(SessionFactory sessionFactory) {
        IRatingDao<Rating> ratingDao = new DefaultRatingDao<>(sessionFactory);
        ratingDao.setClazz(Rating.class);
        return ratingDao;
    }
}
