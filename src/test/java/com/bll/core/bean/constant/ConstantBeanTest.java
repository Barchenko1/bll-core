package com.bll.core.bean.constant;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.bll.core.bean.ConstantBeanConfiguration;
import com.bll.core.util.TestUtil;
import com.core.im.tenant.constant.OrderStatusEnum;
import com.core.im.tenant.constant.ProductStatusEnum;
import com.core.im.tenant.constant.RateEnum;
import com.core.im.tenant.modal.order.OrderStatus;
import com.core.im.tenant.modal.product.Brand;
import com.core.im.tenant.modal.product.Category;
import com.core.im.tenant.modal.product.ProductStatus;
import com.core.im.tenant.modal.product.ProductType;
import com.core.im.tenant.modal.product.Rating;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

@ExtendWith(DBUnitExtension.class)
@DataSet(cleanAfter = true)
public class ConstantBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        ConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConnectionPoolType.HIKARI
        );
        sessionFactory = configurationSessionFactory.getSessionFactory();
        dataSource = getHikariDataSource();
        connectionHolder = dataSource::getConnection;
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/constant/initCategoryDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/constant/initOrderStatusDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/constant/initProductTypeDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/constant/initBrandDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/constant/initRatingDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/constant/initProductStatusDataSet.xml");
        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ConstantBeanConfiguration.class);
    }

    @Test
    void brandConstantDaoBeanTest() {
        String[] beanNames = context.getBeanDefinitionNames();
        Map<String, Brand> brandMap = context.getBean("brandMap", Map.class);
        Assertions.assertFalse(brandMap.isEmpty());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void categoryConstantDaoBeanTest() {
        String[] beanNames = context.getBeanDefinitionNames();
        Map<String, Category> categoryMap =
                (Map<String, Category>) context.getBean("categoryMap");
        Assertions.assertFalse(categoryMap.isEmpty());
    }

    @Test
    void orderStatusConstantDaoBeanTest() {
        String[] beanNames = context.getBeanDefinitionNames();
        Map<OrderStatusEnum, OrderStatus> orderStatusMap =
                (Map<OrderStatusEnum, OrderStatus>) context.getBean("orderStatusMap");
        Assertions.assertFalse(orderStatusMap.isEmpty());
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void productStatusConstantDaoBeanTest() {
        String[] beanNames = context.getBeanDefinitionNames();
        Map<ProductStatusEnum, ProductStatus> productStatusMap =
                (Map<ProductStatusEnum, ProductStatus>) context.getBean("productStatusMap");
        Assertions.assertFalse(productStatusMap.isEmpty());
    }

    @Test
    void productTypeConstantDaoBeanTest() {
        String[] beanNames = context.getBeanDefinitionNames();
        Map<String, ProductType> productTypeMap =
                (Map<String, ProductType>) context.getBean("productTypeMap");
        Assertions.assertFalse(productTypeMap.isEmpty());
    }

    @Test
    void ratingConstantDaoBeanTest() {
        String[] beanNames = context.getBeanDefinitionNames();
        Map<RateEnum, Rating> ratingMap =
                (Map<RateEnum, Rating>) context.getBean("ratingMap");
        Assertions.assertFalse(ratingMap.isEmpty());
    }
}
