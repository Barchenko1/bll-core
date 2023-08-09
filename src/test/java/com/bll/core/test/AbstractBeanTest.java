package com.bll.core.test;

import com.bll.core.util.TestUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

import static com.bll.core.constant.Constant.POSTGRES_DB_URL;
import static com.bll.core.constant.Constant.POSTGRES_USERNAME;
import static com.bll.core.constant.Constant.POSTGRES_PASSWORD;
import static com.bll.core.constant.Constant.POSTGRES_DRIVER;

public abstract class AbstractBeanTest {
    protected static SessionFactory sessionFactory;
    protected static DataSource dataSource;
    protected static AnnotationConfigApplicationContext context;

    @AfterAll
    public static void cleanUp() {
        context.close();
        TestUtil.cleanUp(sessionFactory);
    }

    protected static HikariDataSource getHikariDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(POSTGRES_DB_URL);
        dataSource.setUsername(POSTGRES_USERNAME);
        dataSource.setPassword(POSTGRES_PASSWORD);
        dataSource.setDriverClassName(POSTGRES_DRIVER);
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        return dataSource;
    }
}
