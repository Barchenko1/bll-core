package com.bll.core.service.appuser;

import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.RegistrationAppUserDto;

public interface IAppUserService {
    void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum);

}
