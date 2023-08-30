package com.bll.core.bean;

import com.core.im.tenant.constant.OptionGroupEnum;
import com.core.im.tenant.constant.OrderStatusEnum;
import com.core.im.tenant.constant.ProductStatusEnum;
import com.core.im.tenant.modal.option.OptionGroup;
import com.core.im.tenant.modal.order.OrderStatus;
import com.core.im.tenant.modal.product.Brand;
import com.core.im.tenant.modal.product.Category;
import com.core.im.tenant.modal.product.ProductStatus;
import com.core.im.tenant.modal.product.ProductType;
import com.core.im.tenant.modal.product.Rating;
import com.cos.core.dao.option.IOptionGroupDao;
import com.cos.core.dao.order.IOrderStatusDao;
import com.cos.core.dao.product.IBrandDao;
import com.cos.core.dao.product.ICategoryDao;
import com.cos.core.dao.product.IProductStatusDao;
import com.cos.core.dao.product.IProductTypeDao;
import com.cos.core.dao.rating.IRatingDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ConstantBeanConfiguration {

    @Bean
    public Map<OrderStatusEnum, OrderStatus> orderStatusMap(IOrderStatusDao orderStatusDao) {
        List<OrderStatus> orderStatusList = orderStatusDao.getEntityListBySQLQuery("SELECT * FROM order_status o;");
        return orderStatusList.stream()
                .collect(Collectors.toMap(OrderStatus::getName, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Category> categoryMap(ICategoryDao categoryDao) {
        List<Category> categoryList = categoryDao.getEntityListBySQLQuery("SELECT * FROM category c");
        return categoryList.stream()
                .collect(Collectors.toMap(Category::getName, category -> category, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ProductType> productTypeMap(IProductTypeDao productTypeDao) {
        List<ProductType> productTypeList = productTypeDao.getEntityListBySQLQuery("SELECT * FROM product_type p");
        return productTypeList.stream()
                .collect(Collectors.toMap(ProductType::getName, productType -> productType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Brand> brandMap(IBrandDao brandDao) {
        List<Brand> brandList = brandDao.getEntityListBySQLQuery("SELECT * FROM brand b");
        return brandList.stream()
                .collect(Collectors.toMap(Brand::getName, brand -> brand, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Integer, Rating> ratingMap(IRatingDao ratingDao) {
        List<Rating> ratingList = ratingDao.getEntityListBySQLQuery("SELECT * FROM rating r");
        return ratingList.stream()
                .collect(Collectors.toMap(Rating::getValue, value -> value, (existing, replacement) -> existing));
    }

    @Bean
    public Map<ProductStatusEnum, ProductStatus> productStatusMap(IProductStatusDao productStatusDao) {
        List<ProductStatus> productStatusList =
                productStatusDao.getEntityListBySQLQuery("SELECT * FROM product_status p");
        return productStatusList.stream()
                .collect(Collectors.toMap(ProductStatus::getName, productStatus -> productStatus, (existing, replacement) -> existing));
    }

    @Bean
    public Map<OptionGroupEnum, OptionGroup> optionGroupMap(IOptionGroupDao optionGroupDao) {
        List<OptionGroup> optionGroupList =
                optionGroupDao.getEntityListBySQLQuery("SELECT * FROM option_group o");
        return optionGroupList.stream()
                .collect(Collectors.toMap(OptionGroup::getOptionGroup, optionGroup -> optionGroup, (existing, replacement) -> existing));
    }
}
