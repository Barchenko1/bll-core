package com.bll.core.bean;

import com.core.im.modal.user.AppUser;
import com.cos.core.config.ConfigDbType;
import com.cos.core.config.ConnectionPoolType;
import com.cos.core.config.factory.ConfigurationSessionFactory;
import com.cos.core.config.factory.IConfigurationSessionFactory;
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
               ConnectionPoolType.HIKARI, ConfigDbType.XML, new Class[]{AppUser.class}
        );
        return configurationSessionFactory.getSessionFactory();
    }
}
