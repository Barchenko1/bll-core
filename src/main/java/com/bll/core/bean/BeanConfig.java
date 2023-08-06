package com.bll.core.bean;

import com.bll.core.service.UserService;
import com.core.im.modal.user.AppUser;
import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.config.factory.IConfigurationSessionFactory;
import com.cos.core.dao.impl.DefaultAppUserDao;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public HelloBean helloBean() {
        HelloBean helloBean = new HelloBean();
        helloBean.setMessage("Hello, Spring Context!");
        return helloBean;
    }

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
    public UserService userService(SessionFactory sessionFactory) {
        return new UserService(sessionFactory, appUserDao(sessionFactory));
    }

    private Class[] getDbClasses() {
        return new Class[]{
                AppUser.class
        };
    }
}
