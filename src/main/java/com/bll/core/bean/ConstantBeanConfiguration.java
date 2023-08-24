package com.bll.core.bean;

import com.core.im.tenant.constant.OrderStatusEnum;
import com.core.im.tenant.constant.ProductStatusEnum;
import com.core.im.tenant.modal.order.OrderStatus;
import com.core.im.tenant.modal.product.Brand;
import com.core.im.tenant.modal.product.Category;
import com.core.im.tenant.modal.product.ProductStatus;
import com.core.im.tenant.modal.product.ProductType;
import com.core.im.tenant.modal.product.Rating;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.product.IBrandDao;
import com.cos.core.dao.product.ICategoryDao;
import com.cos.core.dao.product.IProductStatusDao;
import com.cos.core.dao.product.IProductTypeDao;
import com.cos.core.dao.rating.IRatingDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ConstantBeanConfiguration {

    @Bean
    public Map<OrderStatusEnum, OrderStatus> orderStatusMap(IOrderStatusDao<OrderStatus> orderStatusDao) {
        return orderStatusDao.getEntityListBySQLQuery("SELECT * FROM order_status o;").stream()
                .collect(Collectors.toMap(OrderStatus::getName, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Category> categoryMap(ICategoryDao<Category> categoryDao) {
        return categoryDao.getEntityListBySQLQuery("SELECT * FROM category c").stream()
                .collect(Collectors.toMap(Category::getName, category -> category, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ProductType> productTypeMap(IProductTypeDao<ProductType> productTypeDao) {
        return productTypeDao.getEntityListBySQLQuery("SELECT * FROM product_type p").stream()
                .collect(Collectors.toMap(ProductType::getName, productType -> productType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Brand> brandMap(IBrandDao<Brand> brandDao) {
        return brandDao.getEntityListBySQLQuery("SELECT * FROM brand b").stream()
                .collect(Collectors.toMap(Brand::getName, brand -> brand, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Integer, Rating> ratingMap(IRatingDao<Rating> ratingDao) {
        return ratingDao.getEntityListBySQLQuery("SELECT * FROM rating r").stream()
                .collect(Collectors.toMap(Rating::getValue, rating -> rating, (existing, replacement) -> existing));
    }

    @Bean
    public Map<ProductStatusEnum, ProductStatus> productStatusMap(IProductStatusDao<ProductStatus> productStatusDao) {
        return productStatusDao.getEntityListBySQLQuery("SELECT * FROM product_status p").stream()
                .collect(Collectors.toMap(ProductStatus::getName, productStatus -> productStatus, (existing, replacement) -> existing));
    }
}
