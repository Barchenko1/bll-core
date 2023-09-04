package com.bll.core.bean.constant;

import com.bll.core.bean.AbstractBeanTest;
import com.bll.core.bean.BeanConfiguration;
import com.bll.core.bean.ConstantBeanConfiguration;
import com.bll.core.mapper.TestUtil;
import com.core.im.tenant.constant.OptionGroupEnum;
import com.core.im.tenant.constant.OrderStatusEnum;
import com.core.im.tenant.constant.ProductStatusEnum;
import com.core.im.tenant.constant.RateEnum;
import com.core.im.tenant.modal.option.OptionGroup;
import com.core.im.tenant.modal.order.OrderStatus;
import com.core.im.tenant.modal.product.Brand;
import com.core.im.tenant.modal.product.Category;
import com.core.im.tenant.modal.product.ProductStatus;
import com.core.im.tenant.modal.product.ProductType;
import com.core.im.tenant.modal.product.Rating;
import com.core.im.tenant.modal.user.UserRole;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.DBUnitExtension;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

@ExtendWith({DBUnitExtension.class})
@DataSet(cleanAfter = true)
public class ConstantBeanTest extends AbstractBeanTest {
    private static ConnectionHolder connectionHolder;

    @BeforeAll
    public static void getSessionFactory() {
        dataSource = getTenantHikariDataSource();
        connectionHolder = dataSource::getConnection;
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initUserRoleDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initCategoryDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initOrderStatusDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initProductTypeDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initBrandDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initRatingDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initProductStatusDataSet.xml");
        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initOptionGroupDataSet.xml");

        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ConstantBeanConfiguration.class);
        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
    }

    @Test
    void userRoleConstantDaoBeanTest() {
        Map<String, UserRole> userRoleMap = context.getBean("userRoleMap", Map.class);
        Assertions.assertFalse(userRoleMap.isEmpty());
    }

    @Test
    void brandConstantDaoBeanTest() {
        Map<String, Brand> brandMap = context.getBean("brandMap", Map.class);
        Assertions.assertFalse(brandMap.isEmpty());
    }

    @Test
    void categoryConstantDaoBeanTest() {
        Map<String, Category> categoryMap = context.getBean("categoryMap", Map.class);
        Assertions.assertFalse(categoryMap.isEmpty());
    }

    @Test
    void orderStatusConstantDaoBeanTest() {
        Map<OrderStatusEnum, OrderStatus> orderStatusMap = context.getBean("orderStatusMap", Map.class);
        Assertions.assertFalse(orderStatusMap.isEmpty());
    }

    @Test
    void productStatusConstantDaoBeanTest() {
        Map<ProductStatusEnum, ProductStatus> productStatusMap = context.getBean("productStatusMap", Map.class);
        Assertions.assertFalse(productStatusMap.isEmpty());
    }

    @Test
    void productTypeConstantDaoBeanTest() {
        Map<String, ProductType> productTypeMap = context.getBean("productTypeMap", Map.class);
        Assertions.assertFalse(productTypeMap.isEmpty());
    }

    @Test
    void ratingConstantDaoBeanTest() {
        Map<RateEnum, Rating> ratingMap = context.getBean("ratingMap", Map.class);
        Assertions.assertFalse(ratingMap.isEmpty());
    }

    @Test
    void optionGroupConstantDaoBeanTest() {
        Map<OptionGroupEnum, OptionGroup> optionGroupMap = context.getBean("optionGroupMap", Map.class);
        Assertions.assertFalse(optionGroupMap.isEmpty());
    }

}
