package com.bll.core.service.appuser;

import com.bll.core.util.IDtoEntityMapper;
import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.RegistrationAppUserDto;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserAddress;
import com.core.im.tenant.modal.user.UserDetail;
import com.core.im.tenant.modal.user.UserPayment;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AppUserService implements IAppUserService {

    private final ITransactionManager clientTransactionManager;
    private final IAppUserDao appUserDao;
    private final Map<String, UserRole> userRoleMap;
    private final IDtoEntityMapper dtoEntityMapper;

    @Autowired
    public AppUserService(@Qualifier("clientTransactionManager")ITransactionManager clientTransactionManager,
                          IAppUserDao appUserDao,
                          Map<String, UserRole> userRoleMap,
                          IDtoEntityMapper dtoEntityMapper) {
        this.clientTransactionManager = clientTransactionManager;
        this.appUserDao = appUserDao;
        this.userRoleMap = userRoleMap;
        this.dtoEntityMapper = dtoEntityMapper;
    }

    @Override
    public void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum) {
        AppUser createNewUser = new AppUser();
        dtoEntityMapper.mapDtoToEntity(registrationAppUserDto, createNewUser);
        createNewUser.setRole(getUserRole(roleEnum));

        List<?> list = new ArrayList<>(){{
            UserPayment userPayment = createNewUser.getUserPayment();
            if (userPayment != null) {
                add(userPayment);
            }
            UserAddress userAddress = createNewUser.getUserAddress();
            if (userAddress != null) {
                add(userAddress);
            }
            UserDetail userDetail = createNewUser.getUserDetail();
            if (userDetail != null) {
                add(userDetail);
            }
            add(createNewUser);
        }};

        clientTransactionManager.useTransaction(list);
    }

    private UserRole getUserRole(RoleEnum roleEnum) {
        return userRoleMap.get(roleEnum.getValue());
    }

}
