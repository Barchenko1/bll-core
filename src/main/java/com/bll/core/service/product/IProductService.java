package com.bll.core.service.product;

import com.core.im.tenant.constant.RoleEnum;
import com.core.im.tenant.dto.request.post.CreateProductDto;

public interface IProductService {

    public void createNewProduct(CreateProductDto createProductDto, RoleEnum roleEnum);
}
