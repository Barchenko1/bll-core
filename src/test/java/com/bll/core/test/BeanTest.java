package com.bll.core.test;

import com.bll.core.bean.BeanConfiguration;
import com.core.im.modal.user.AppUser;
import com.cos.core.dao.user.IAppUserDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class BeanTest {

    @Autowired
    private IAppUserDao<AppUser> appUserDao;

    @BeforeAll
    public static void setup() {

    }

    @Test
    void test() {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(BeanConfiguration.class)) {
            String[] beanNames = context.getBeanDefinitionNames();
            IAppUserDao<AppUser> appUserDao = context.getBean(IAppUserDao.class);
            List<AppUser> appUserList = appUserDao.getEntityListBySQLQuery("select * from app_user a;");

            System.out.println("test");
        }
    }
}
