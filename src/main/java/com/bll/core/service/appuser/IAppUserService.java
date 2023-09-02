package com.bll.core.service.appuser;

import com.core.im.tenant.dto.request.AppUserDto;
import com.core.im.tenant.dto.request.RegistrationAppUserDto;

public interface IAppUserService {
    <E extends AppUserDto> void createNewUser(E appUserDto);
}
