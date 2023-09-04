package com.bll.core.bean;

import com.bll.core.service.appuser.AppUserService;
import com.bll.core.service.appuser.IAppUserService;
import com.bll.core.util.DtoEntityMapper;
import com.bll.core.util.IDtoEntityMapper;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.org.ITenantConfigDao;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceBeanConfiguration {

    @Bean
    public IDtoEntityMapper dtoEntityMapper() {
        return new DtoEntityMapper();
    }

    @Bean
    public IAppUserService appUserService(ITransactionManager transactionManager, IAppUserDao appUserDao, Map<String, UserRole> userRoleMap, IDtoEntityMapper dtoEntityMapper) {
        return new AppUserService(transactionManager, appUserDao, userRoleMap, dtoEntityMapper);
    }

}
