package com.bll.core.bean;

import com.core.im.org.modal.OrgUser;
import com.core.im.org.modal.Organization;
import com.core.im.org.modal.Tenant;
import com.core.im.org.modal.TenantConfiguration;
import com.core.im.tenant.modal.bucket.Bucket;
import com.core.im.tenant.modal.business.BusinessAddress;
import com.core.im.tenant.modal.business.Shop;
import com.core.im.tenant.modal.business.Store;
import com.core.im.tenant.modal.liked.Liked;
import com.core.im.tenant.modal.option.Option;
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
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.config.factory.IConfigurationSessionFactory;
import com.cos.core.dao.basic.BasicAppUserDao;
import com.cos.core.dao.basic.BasicBrandDao;
import com.cos.core.dao.basic.BasicBucketDao;
import com.cos.core.dao.basic.BasicBusinessAddressDao;
import com.cos.core.dao.basic.BasicCategoryDao;
import com.cos.core.dao.basic.BasicCommentDao;
import com.cos.core.dao.basic.BasicDiscountDao;
import com.cos.core.dao.basic.BasicLikedDao;
import com.cos.core.dao.basic.BasicOptionDao;
import com.cos.core.dao.basic.BasicOptionGroupDao;
import com.cos.core.dao.basic.BasicOrderAddressDao;
import com.cos.core.dao.basic.BasicOrderDetailDao;
import com.cos.core.dao.basic.BasicOrderHistoryDao;
import com.cos.core.dao.basic.BasicOrderItemDao;
import com.cos.core.dao.basic.BasicOrderStatusDao;
import com.cos.core.dao.basic.BasicOrgUserDao;
import com.cos.core.dao.basic.BasicOrganizationDao;
import com.cos.core.dao.basic.BasicPostDao;
import com.cos.core.dao.basic.BasicProductDao;
import com.cos.core.dao.basic.BasicProductStatusDao;
import com.cos.core.dao.basic.BasicProductTypeDao;
import com.cos.core.dao.basic.BasicRatingDao;
import com.cos.core.dao.basic.BasicReviewDao;
import com.cos.core.dao.basic.BasicShopDao;
import com.cos.core.dao.basic.BasicStoreDao;
import com.cos.core.dao.basic.BasicTenantConfigurationDao;
import com.cos.core.dao.basic.BasicTenantDao;
import com.cos.core.dao.basic.BasicUserAddressDao;
import com.cos.core.dao.basic.BasicUserDetailDao;
import com.cos.core.dao.basic.BasicUserPaymentDao;
import com.cos.core.dao.basic.BasicUserRoleDao;
import com.cos.core.dao.basic.BasicViewedDao;
import com.cos.core.dao.bucket.IBucketDao;
import com.cos.core.dao.business.IBusinessAddressDao;
import com.cos.core.dao.business.IShopDao;
import com.cos.core.dao.business.IStoreDao;
import com.cos.core.dao.liked.ILikedDao;
import com.cos.core.dao.option.IOptionDao;
import com.cos.core.dao.option.IOptionGroupDao;
import com.cos.core.dao.order.IOrderAddressDao;
import com.cos.core.dao.order.IOrderDetailDao;
import com.cos.core.dao.order.IOrderHistoryDao;
import com.cos.core.dao.order.IOrderItemDao;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.org.IOrgUserDao;
import com.cos.core.dao.org.IOrganizationDao;
import com.cos.core.dao.org.ITenantConfigDao;
import com.cos.core.dao.org.ITenantDao;
import com.cos.core.dao.post.ICommentDao;
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
import com.cos.core.dto.BasicDtoEntityDao;
import com.cos.core.dto.IDtoEntityDao;
import com.cos.core.transaction.BasicTransactionManager;
import com.cos.core.transaction.ITransactionManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BeanConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
               ConnectionPoolType.HIKARI
        );
        return configurationSessionFactory.getSessionFactory();
    }

    @Bean
    public SessionFactory tenantSessionFactory() {
        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                "tenant.hikari.hibernate.cfg.xml"
        );
        return configurationSessionFactory.getSessionFactory();
    }

    @Bean
    @Primary
    @Qualifier("clientTransactionManager")
    public ITransactionManager clientTransactionManager(SessionFactory sessionFactory) {
        return new BasicTransactionManager(
                sessionFactory
        );
    }

    @Bean
    @Qualifier("orgTransactionManager")
    public ITransactionManager orgTransactionManager(SessionFactory tenantSessionFactory) {
        return new BasicTransactionManager(
                tenantSessionFactory
        );
    }

    @Bean
    public Map<String, ITransactionManager> transactionManagerMap() {
        return new HashMap<>() {{
            put("clientTransactionManager", clientTransactionManager(sessionFactory()));
            put("orgTransactionManager", orgTransactionManager(tenantSessionFactory()));
        }};
    }

    //dto bean example
    @Bean
    public IDtoEntityDao dtoEntityDao(SessionFactory sessionFactory) {
        return new BasicDtoEntityDao(sessionFactory);
    }

    @Bean
    public ITenantConfigDao tenantConfigurationDao(SessionFactory tenantSessionFactory) {
        ITenantConfigDao tenantConfigDao = new BasicTenantConfigurationDao(tenantSessionFactory);
        tenantConfigDao.setClazz(TenantConfiguration.class);
        return tenantConfigDao;
    }

    @Bean
    public ITenantDao tenantDao(SessionFactory tenantSessionFactory) {
        ITenantDao tenantConfigDao = new BasicTenantDao(tenantSessionFactory);
        tenantConfigDao.setClazz(Tenant.class);
        return tenantConfigDao;
    }

    @Bean
    public IOrganizationDao organizationDao(SessionFactory tenantSessionFactory) {
        IOrganizationDao organizationDao = new BasicOrganizationDao(tenantSessionFactory);
        organizationDao.setClazz(Organization.class);
        return organizationDao;
    }

    @Bean
    public IOrgUserDao orgUserDao(SessionFactory tenantSessionFactory) {
        IOrgUserDao organizationDao = new BasicOrgUserDao(tenantSessionFactory);
        organizationDao.setClazz(OrgUser.class);
        return organizationDao;
    }

    @Bean
    public IAppUserDao appUserDao(SessionFactory sessionFactory) {
        IAppUserDao appUserDao = new BasicAppUserDao(sessionFactory);
        appUserDao.setClazz(AppUser.class);
        return appUserDao;
    }

    @Bean
    public IUserRoleDao userRoleDao(SessionFactory sessionFactory) {
        IUserRoleDao userRoleDao = new BasicUserRoleDao(sessionFactory);
        userRoleDao.setClazz(UserRole.class);
        return userRoleDao;
    }

    @Bean
    public IUserDetailDao userDetailsDao(SessionFactory sessionFactory) {
        IUserDetailDao userDetailsDao = new BasicUserDetailDao(sessionFactory);
        userDetailsDao.setClazz(UserDetail.class);
        return userDetailsDao;
    }

    @Bean
    public IUserPaymentDao userPaymentDao(SessionFactory sessionFactory) {
        IUserPaymentDao userPaymentDao = new BasicUserPaymentDao(sessionFactory);
        userPaymentDao.setClazz(UserPayment.class);
        return userPaymentDao;
    }

    @Bean
    public IUserAddressDao userAddressDao(SessionFactory sessionFactory) {
        IUserAddressDao userAddressDao = new BasicUserAddressDao(sessionFactory);
        userAddressDao.setClazz(UserAddress.class);
        return userAddressDao;
    }

    @Bean
    public IOrderItemDao orderItemDao(SessionFactory sessionFactory) {
        IOrderItemDao orderItemDao = new BasicOrderItemDao(sessionFactory);
        orderItemDao.setClazz(OrderItem.class);
        return orderItemDao;
    }

    @Bean
    public IOrderStatusDao orderStatusDao(SessionFactory sessionFactory) {
        IOrderStatusDao orderStatusDao = new BasicOrderStatusDao(sessionFactory);
        orderStatusDao.setClazz(OrderStatus.class);
        return orderStatusDao;
    }

    @Bean
    public IOrderDetailDao orderDetailDao(SessionFactory sessionFactory) {
        IOrderDetailDao orderDetailDao = new BasicOrderDetailDao(sessionFactory);
        orderDetailDao.setClazz(OrderDetail.class);
        return orderDetailDao;
    }

    @Bean
    public IOrderAddressDao orderAddressDao(SessionFactory sessionFactory) {
        IOrderAddressDao orderAddressDao = new BasicOrderAddressDao(sessionFactory);
        orderAddressDao.setClazz(OrderAddress.class);
        return orderAddressDao;
    }

    @Bean
    public IOrderHistoryDao orderHistoryDao(SessionFactory sessionFactory) {
        IOrderHistoryDao orderHistoryDao = new BasicOrderHistoryDao(sessionFactory);
        orderHistoryDao.setClazz(OrderHistory.class);
        return orderHistoryDao;
    }

    @Bean
    public IProductTypeDao productTypeDao(SessionFactory sessionFactory) {
        IProductTypeDao productTypeDao = new BasicProductTypeDao(sessionFactory);
        productTypeDao.setClazz(ProductType.class);
        return productTypeDao;
    }

    @Bean
    public IDiscountDao discountDao(SessionFactory sessionFactory) {
        IDiscountDao discountDao = new BasicDiscountDao(sessionFactory);
        discountDao.setClazz(Discount.class);
        return discountDao;
    }

    @Bean
    public IBrandDao brandDao(SessionFactory sessionFactory) {
        IBrandDao brandDao = new BasicBrandDao(sessionFactory);
        brandDao.setClazz(Brand.class);
        return brandDao;
    }

    @Bean
    public ICategoryDao categoryDao(SessionFactory sessionFactory) {
        ICategoryDao categoryDao = new BasicCategoryDao(sessionFactory);
        categoryDao.setClazz(Category.class);
        return categoryDao;
    }

    @Bean
    public IProductStatusDao productStatusDao(SessionFactory sessionFactory) {
        IProductStatusDao productStatusDao = new BasicProductStatusDao(sessionFactory);
        productStatusDao.setClazz(ProductStatus.class);
        return productStatusDao;
    }

    @Bean
    public IPostDao postDao(SessionFactory sessionFactory) {
        IPostDao postDao = new BasicPostDao(sessionFactory);
        postDao.setClazz(Post.class);
        return postDao;
    }

    @Bean
    public IBusinessAddressDao shopAddressDao(SessionFactory sessionFactory) {
        IBusinessAddressDao businessAddressDao = new BasicBusinessAddressDao(sessionFactory);
        businessAddressDao.setClazz(BusinessAddress.class);
        return businessAddressDao;
    }

    @Bean
    public IShopDao shopDao(SessionFactory sessionFactory) {
        IShopDao shopDao = new BasicShopDao(sessionFactory);
        shopDao.setClazz(Shop.class);
        return shopDao;
    }

    @Bean
    public IStoreDao storeDao(SessionFactory sessionFactory) {
        IStoreDao storeDao = new BasicStoreDao(sessionFactory);
        storeDao.setClazz(Store.class);
        return storeDao;
    }

    @Bean
    public IOptionGroupDao optionGroupDao(SessionFactory sessionFactory) {
        IOptionGroupDao optionGroupDao = new BasicOptionGroupDao(sessionFactory);
        optionGroupDao.setClazz(OptionGroup.class);
        return optionGroupDao;
    }

    @Bean
    public IOptionDao optionDao(SessionFactory sessionFactory) {
        IOptionDao optionDao = new BasicOptionDao(sessionFactory);
        optionDao.setClazz(Option.class);
        return optionDao;
    }

    @Bean
    public IBucketDao bucketDao(SessionFactory sessionFactory) {
        IBucketDao bucketDao = new BasicBucketDao(sessionFactory);
        bucketDao.setClazz(Bucket.class);
        return bucketDao;
    }

    @Bean
    public ILikedDao likedDao(SessionFactory sessionFactory) {
        ILikedDao likedDao = new BasicLikedDao(sessionFactory);
        likedDao.setClazz(Liked.class);
        return likedDao;
    }

    @Bean
    public IViewedDao viewedDao(SessionFactory sessionFactory) {
        IViewedDao viewedDao = new BasicViewedDao(sessionFactory);
        viewedDao.setClazz(Viewed.class);
        return viewedDao;
    }

    @Bean
    public IProductDao productDao(SessionFactory sessionFactory) {
        IProductDao productDao = new BasicProductDao(sessionFactory);
        productDao.setClazz(Product.class);
        return productDao;
    }

    @Bean
    public IReviewDao reviewDao(SessionFactory sessionFactory) {
        IReviewDao reviewDao = new BasicReviewDao(sessionFactory);
        reviewDao.setClazz(Review.class);
        return reviewDao;
    }

    @Bean
    public IRatingDao ratingDao(SessionFactory sessionFactory) {
        IRatingDao ratingDao = new BasicRatingDao(sessionFactory);
        ratingDao.setClazz(Rating.class);
        return ratingDao;
    }
}
