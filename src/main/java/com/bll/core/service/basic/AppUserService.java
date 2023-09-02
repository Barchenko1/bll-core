package com.bll.core.service.basic;

import com.bll.core.service.appuser.IAppUserService;
import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.RegistrationAppUserDto;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AppUserService implements IAppUserService {

    private final ITransactionManager transactionManager;
    private final IAppUserDao appUserDao;
    private final Map<String, UserRole> userRoleMap;

    @Autowired
    public AppUserService(ITransactionManager transactionManager, IAppUserDao appUserDao, Map<String, UserRole> userRoleMap) {
        this.transactionManager = transactionManager;
        this.appUserDao = appUserDao;
        this.userRoleMap = userRoleMap;
    }

    @Override
    public <E> void createNewUser(RegistrationAppUserDto appUserDto) {
        UserRole userRole = userRoleMap.get(RoleEnum.ROLE_USER.getValue());

        AppUser appUser = new AppUser();
        appUser.setEmail(appUserDto.getEmail());
        appUser.setPassword(appUserDto.getPassword());
        appUser.setRole(userRole);
        appUser.setDateOfCreate(System.currentTimeMillis());

        appUserDao.saveEntity(appUser);
    }

}
