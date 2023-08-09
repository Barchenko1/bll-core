package com.bll.core.bean;

import com.core.im.modal.order.OrderStatus;
import com.cos.core.dao.order.IOrderStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityBeanConfiguration {

    @Autowired
    IOrderStatusDao<OrderStatus> orderStatusDao;

    @Bean
    public OrderStatus orderStatusNew() {
        return orderStatusDao.getEntityBySQLQuery("select * from order_status o where o.status = NEW;");
    }

    @Bean
    public OrderStatus orderStatusInProgress() {
        return orderStatusDao.getEntityBySQLQuery("select * from order_status o where o.status = IN_PROGRESS;");
    }

    @Bean
    public OrderStatus orderStatusComplete() {
        return orderStatusDao.getEntityBySQLQuery("select * from order_status o where o.status = COMPLETE;");
    }

    @Bean
    public OrderStatus orderStatusTerminated() {
        return orderStatusDao.getEntityBySQLQuery("select * from order_status o where o.status = TERMINATED;");
    }
}
