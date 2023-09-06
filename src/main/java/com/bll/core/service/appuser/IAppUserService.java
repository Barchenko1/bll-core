package com.bll.core.service.appuser;

import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.post.RegistrationAppUserDto;
import com.core.im.tenant.dto.request.put.UpdateUserDetailDto;

public interface IAppUserService {
    void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum);
    void updateUserDetails(UpdateUserDetailDto userDetailDto);

}
