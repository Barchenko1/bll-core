package com.bll.core.service.appuser;

import com.bll.core.mapper.IDtoEntityMapper;
import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.post.RegistrationAppUserDto;
import com.core.im.tenant.dto.request.put.UpdateUserDetailDto;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserAddress;
import com.core.im.tenant.modal.user.UserDetail;
import com.core.im.tenant.modal.user.UserPayment;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserDetailDao;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppUserService implements IAppUserService {

    private final ITransactionManager clientTransactionManager;
    private final IAppUserDao appUserDao;
    private final IUserDetailDao userDetailDao;
    private final Map<String, UserRole> userRoleMap;
    private final IDtoEntityMapper dtoEntityMapper;

    @Autowired
    public AppUserService(@Qualifier("clientTransactionManager")ITransactionManager clientTransactionManager,
                          IAppUserDao appUserDao,
                          IUserDetailDao userDetailDao,
                          Map<String, UserRole> userRoleMap,
                          IDtoEntityMapper dtoEntityMapper) {
        this.clientTransactionManager = clientTransactionManager;
        this.appUserDao = appUserDao;
        this.userDetailDao = userDetailDao;
        this.userRoleMap = userRoleMap;
        this.dtoEntityMapper = dtoEntityMapper;
    }

    @Override
    public void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum) {
        AppUser createNewUser = new AppUser();
        dtoEntityMapper.mapDtoToEntity(registrationAppUserDto, createNewUser);
        createNewUser.setRole(getUserRole(roleEnum));

        List<?> list = getTransactionObjectList(createNewUser);

        clientTransactionManager.useTransaction(list);
    }

    @Override
    public void updateUserDetails(UpdateUserDetailDto userDetailDto) {
        Optional<UserDetail> userDetailOpt = userDetailDao.getOptionEntityBySQLQuery("");
        if (userDetailOpt.isEmpty()) {
            throw new RuntimeException();
        }
        UserDetail userDetail = userDetailOpt.get();
        dtoEntityMapper.mapDtoToEntity(userDetailDto, userDetail);
        userDetail.setFirstName("Man");
        userDetailDao.updateEntity(userDetail);
    }

    private UserRole getUserRole(RoleEnum roleEnum) {
        return userRoleMap.get(roleEnum.getValue());
    }

    private void getType(RegistrationAppUserDto registrationAppUserDto, AppUser appUser) {
        UserAddress userAddress = new UserAddress();
        dtoEntityMapper.mapDtoToEntity(registrationAppUserDto, userAddress);
        System.out.println(userAddress);

    }

    private List<?> getTransactionObjectList(AppUser appUser) {
        return new ArrayList<>(){{
            UserPayment userPayment = appUser.getUserPayment();
            if (userPayment != null) {
                add(userPayment);
            }
            UserAddress userAddress = appUser.getUserAddress();
            if (userAddress != null) {
                add(userAddress);
            }
            UserDetail userDetail = appUser.getUserDetail();
            if (userDetail != null) {
                add(userDetail);
            }
            add(appUser);
        }};
    }

}
