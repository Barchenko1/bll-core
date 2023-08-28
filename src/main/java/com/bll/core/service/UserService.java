package com.bll.core.service;

import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final SessionFactory sessionFactory;
    private final IAppUserDao appUserDao;

    @Autowired
    public UserService(SessionFactory sessionFactory, IAppUserDao appUserDao) {
        this.sessionFactory = sessionFactory;
        this.appUserDao = appUserDao;
    }


    public <E> void addUser(E user) {
        appUserDao.saveEntity(user);
    }

}
