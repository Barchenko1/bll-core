package com.bll.core.service;

import com.core.im.tenant.modal.user.AppUser;
import com.cos.core.dao.user.IAppUserDao;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService<E> {

    private final SessionFactory sessionFactory;
    private final IAppUserDao<E> appUserDao;

    @Autowired
    public UserService(SessionFactory sessionFactory, IAppUserDao<E> appUserDao) {
        this.sessionFactory = sessionFactory;
        this.appUserDao = appUserDao;
    }


    public void addUser(E user) {
        appUserDao.saveEntity(user);
    }

}
