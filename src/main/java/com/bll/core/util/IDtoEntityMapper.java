package com.bll.core.util;

public interface IDtoEntityMapper {

    public <E, T> void mapDtoToEntity(E dtoObject, T entityObject);
}
