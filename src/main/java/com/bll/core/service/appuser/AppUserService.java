package com.bll.core.service.appuser;

import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.OptionParams;
import com.core.im.tenant.dto.request.RegistrationAppUserDto;
import com.core.im.tenant.modal.user.AppUser;
import com.core.im.tenant.modal.user.UserAddress;
import com.core.im.tenant.modal.user.UserDetail;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class AppUserService {

    private final ITransactionManager transactionManager;
    private final IAppUserDao appUserDao;
    private final Map<String, UserRole> userRoleMap;

    @Autowired
    public AppUserService(ITransactionManager transactionManager, IAppUserDao appUserDao, Map<String, UserRole> userRoleMap) {
        this.transactionManager = transactionManager;
        this.appUserDao = appUserDao;
        this.userRoleMap = userRoleMap;
    }

    public void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum) {
        AppUser createNewUser = new AppUser();
        createNewUser.setEmail(registrationAppUserDto.getEmail());
        createNewUser.setPassword(registrationAppUserDto.getPassword());
        createNewUser.setRole(getUserRole(roleEnum));

        OptionParams optionParams = registrationAppUserDto.getOptionParams();
        String username = (String) optionParams.getOrDefault("username", null);


        UserAddress userAddress = getUserAddress(optionParams);
        UserDetail userDetail = getUserDetail(optionParams);

        List<?> list = Arrays.asList(createNewUser, userAddress, userDetail);
        transactionManager.useTransaction(list);
    }

    private UserAddress getUserAddress(OptionParams optionParams) {
        String street = (String) optionParams
                .getOrDefault("street", null);

        UserAddress userAddress = new UserAddress();
        userAddress.setStreet(street);

        return userAddress;
    }

    private UserRole getUserRole(RoleEnum roleEnum) {
        return userRoleMap.get(roleEnum.getValue());
    }
    
    private UserDetail getUserDetail(OptionParams optionParams) {
        String firstName = (String) optionParams
                .getOrDefault("firstName", null);
        String surName = (String) optionParams
                .getOrDefault("surName", null);
        String phone = (String) optionParams
                .getOrDefault("phone", null);

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName(firstName);
        userDetail.setSurName(surName);
        userDetail.setPhone(phone);
        
        return userDetail;
    }

}
