package com.bll.core.service.product;

import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.post.CreateProductDto;
import com.core.im.tenant.modal.user.UserRole;
import com.cos.core.mapper.IDtoEntityMapper;
import com.cos.core.transaction.ITransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductService implements IProductService {

    private final ITransactionManager clientTransactionManager;
    private final Map<String, UserRole> userRoleMap;
    private final IDtoEntityMapper dtoEntityMapper;

    @Autowired
    public ProductService(@Qualifier("clientTransactionManager")ITransactionManager clientTransactionManager,
                          Map<String, UserRole> userRoleMap,
                          IDtoEntityMapper dtoEntityMapper) {
        this.clientTransactionManager = clientTransactionManager;
        this.userRoleMap = userRoleMap;
        this.dtoEntityMapper = dtoEntityMapper;
    }

    @Override
    public void createNewProduct(CreateProductDto createProductDto, RoleEnum roleEnum) {

    }
}
