package com.bll.core.service.appuser;

import com.core.im.tenant.dto.request.RegistrationAppUserDto;

public interface IAppUserService {
    <E> void createNewUser(RegistrationAppUserDto appUserDto);
}
