package com.bll.core.mapper;

import com.google.gson.JsonObject;

import java.util.Optional;

public interface IJsonDtoBindMapper {

    Optional<String> get(String field);
    void setKey(String key);

}
