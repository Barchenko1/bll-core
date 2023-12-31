package com.bll.core.bean;

import com.bll.core.service.appuser.AppUserService;
import com.bll.core.service.appuser.IAppUserService;
import com.bll.core.service.post.IPostService;
import com.bll.core.service.post.PostService;
import com.bll.core.service.product.IProductService;
import com.bll.core.service.product.ProductService;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.dao.post.IPostDao;
import com.cos.core.dao.user.IAppUserDao;
import com.cos.core.dao.user.IUserDetailDao;
import com.cos.core.dto.IDtoEntityDao;
import com.cos.core.mapper.DtoEntityBind;
import com.cos.core.mapper.DtoEntityMapper;
import com.cos.core.mapper.IDtoEntityBind;
import com.cos.core.mapper.IDtoEntityMapper;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceBeanConfiguration {

    @Bean
    public IDtoEntityBind dtoEntityBind() {
        return new DtoEntityBind("bind");
    }

    @Bean
    public IDtoEntityMapper dtoEntityMapper(IDtoEntityBind dtoEntityBind) {
        return new DtoEntityMapper(dtoEntityBind);
    }

    @Bean
    public IAppUserService appUserService(ITransactionManager transactionManager,
                                          IAppUserDao appUserService,
                                          IUserDetailDao userDetailDao,
                                          Map<String, UserRole> userRoleMap,
                                          IDtoEntityMapper dtoEntityMapper) {
        return new AppUserService(transactionManager,
                                    appUserService,
                                    userDetailDao,
                                    userRoleMap,
                                    dtoEntityMapper
        );
    }

    @Bean
    public IPostService postService(ITransactionManager transactionManager,
                                    IPostDao postDao,
                                    IDtoEntityMapper dtoEntityMapper,
                                    IDtoEntityDao dtoEntityDao) {
        return new PostService(transactionManager,
                               postDao,
                               dtoEntityMapper,
                               dtoEntityDao
        );
    }

    @Bean
    public IProductService productService(ITransactionManager transactionManager,
                                          Map<String, UserRole> userRoleMap,
                                          IDtoEntityMapper dtoEntityMapper) {
        return new ProductService(transactionManager, userRoleMap, dtoEntityMapper);
    }

}
