package com.bll.core;

import com.bll.core.bean.BeanConfig;
import com.bll.core.bean.HelloBean;
import com.bll.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    @Autowired
    private static HelloBean helloBean;

    @Autowired
    private static UserService userService;

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(BeanConfig.class)) {
//
//            // Retrieve the bean from the context
//            HelloBean helloBean = context.getBean(HelloBean.class);

            // Use the bean
            System.out.println(helloBean.getMessage());
            userService.toDo();
        }
    }
}