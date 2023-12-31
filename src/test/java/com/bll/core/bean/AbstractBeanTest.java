package com.bll.core.bean;

import com.bll.core.mapper.TestUtil;
import com.cos.core.dao.IEntityDao;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;

import static com.bll.core.constant.Constant.POSTGRES_DRIVER;
import static com.bll.core.constant.Constant.POSTGRES_ORG_DB_URL;
import static com.bll.core.constant.Constant.POSTGRES_ORG_PASSWORD;
import static com.bll.core.constant.Constant.POSTGRES_ORG_USERNAME;
import static com.bll.core.constant.Constant.POSTGRES_TENANT_DB_URL;
import static com.bll.core.constant.Constant.POSTGRES_TENANT_PASSWORD;
import static com.bll.core.constant.Constant.POSTGRES_TENANT_USERNAME;
import static com.bll.core.mapper.QueryPattern.SELECT_BY_PARAM;

public abstract class AbstractBeanTest {

    protected static SessionFactory sessionFactory;
    protected static DataSource dataSource;
    protected static AnnotationConfigApplicationContext context;

    @AfterAll
    public static void cleanUp() throws SQLException {
        context.close();
        TestUtil.cleanUp(sessionFactory);
        dataSource.getConnection().close();
    }

    public static HikariDataSource getTenantHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(POSTGRES_TENANT_DB_URL);
        dataSource.setUsername(POSTGRES_TENANT_USERNAME);
        dataSource.setPassword(POSTGRES_TENANT_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }

    public static HikariDataSource getOrgHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(POSTGRES_ORG_DB_URL);
        dataSource.setUsername(POSTGRES_ORG_USERNAME);
        dataSource.setPassword(POSTGRES_ORG_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }

    protected Object getEntity(IEntityDao entityDao, String table, String paramName, String paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format(SELECT_BY_PARAM, table, paramName, paramValue));
    }

    protected Object getEntity(IEntityDao entityDao, String table, String paramName, Integer paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format(SELECT_BY_PARAM, table, paramName, paramValue));
    }

    protected Object getEntity(IEntityDao entityDao, String table, String paramName, Long paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format(SELECT_BY_PARAM, table, paramName, paramValue));
    }

    protected Object getEntityTest(IEntityDao entityDao, String table, String paramName, Long paramValue) {
        return entityDao.getEntityBySQLQuery(
                String.format("SELECT * FROM %s e JOIN where e.%s = '%s'", table, paramName, paramValue));
    }

    protected <T> void cleanDbByDao(IEntityDao entityDao, String query) {
        List<T> firstOrganizationList = entityDao.getEntityListBySQLQuery(query);
        if (!firstOrganizationList.isEmpty()) {
            firstOrganizationList.forEach(entityDao::deleteEntity);
        }
    }

    protected <T> boolean isTableEmpty(IEntityDao entityDao, String query) {
        List<T> organizationList = entityDao.getEntityListBySQLQuery(query);
        return organizationList.isEmpty();
    }

}
