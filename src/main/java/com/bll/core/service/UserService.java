package com.bll.core.service;

import com.core.im.modal.user.AppUser;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final SessionFactory sessionFactory;
    private final IAppUserDao<AppUser> appUserDao;

    @Autowired
    public UserService(SessionFactory sessionFactory, IAppUserDao<AppUser> appUserDao) {
        this.sessionFactory = sessionFactory;
        this.appUserDao = appUserDao;
    }


    public void toDo() {
        sessionFactory.openSession();

        System.out.println("todo");
    }
}
