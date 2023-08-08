package com.bll.core.test;

import com.bll.core.bean.BeanConfiguration;
import com.bll.core.test.AbstractBeanTest;
import com.bll.core.util.TestUtil;
import com.core.im.constant.CategoryEnum;
import com.core.im.constant.RoleEnum;
import com.core.im.modal.product.Category;
import com.core.im.modal.user.AppUser;
import com.core.im.modal.user.UserRole;
import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.dao.product.ICategoryDao;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserRoleDao;
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
//        context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        sessionFactory = configurationSessionFactory.getSessionFactory();
        dataSource = getHikariDataSource();
        connectionHolder = dataSource::getConnection;
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void appUserDaoBeanTest() {
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/dataset/initDataSet.xml");
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(BeanConfiguration.class)) {
            String[] beanNames = context.getBeanDefinitionNames();
            IAppUserDao<AppUser> appUserDao = context.getBean(IAppUserDao.class);
            List<AppUser> appUserList = appUserDao.getEntityListBySQLQuery("select * from app_user a;");
            Assertions.assertTrue(appUserList.isEmpty());
        }
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void userRoleDaoBeanTest() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(BeanConfiguration.class)) {
            IUserRoleDao<UserRole> userRoleDao = context.getBean(IUserRoleDao.class);
            UserRole userRole = new UserRole();
            userRole.setRoleEnum(RoleEnum.ROLE_USER);
            userRoleDao.saveEntity(userRole);
            List<UserRole> userRoleList = userRoleDao.getEntityListBySQLQuery("select * from user_role a;");
            Assertions.assertFalse(userRoleList.isEmpty());
        }
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true)
    void categoryDaoBeanTest() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(BeanConfiguration.class)) {
            ICategoryDao<Category> categoryDao = context.getBean(ICategoryDao.class);
            Category category = new Category();
            category.setCategoryEnum(CategoryEnum.BEAUTY_AND_HEALTH);
            categoryDao.saveEntity(category);
            List<Category> userRoleList = categoryDao.getEntityListBySQLQuery("select * from category c;");
            Assertions.assertFalse(userRoleList.isEmpty());
        }
    }
}
