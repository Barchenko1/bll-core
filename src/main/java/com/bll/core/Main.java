package com.bll.core;

import com.bll.core.bean.BeanConfig;
import com.bll.core.bean.HelloBean;
import com.bll.core.service.UserService;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(BeanConfig.class)) {
//
//            // Retrieve the bean from the context
            HelloBean helloBean = context.getBean(HelloBean.class);

            // Use the bean
            System.out.println(helloBean.getMessage());
//            UserService userService = new UserService(sessionFactory, null);
//            userService.toDo();
        }
    }
}