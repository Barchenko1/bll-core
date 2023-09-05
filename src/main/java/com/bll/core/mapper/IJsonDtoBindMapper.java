package com.bll.core.mapper;

import com.google.gson.JsonObject;

import java.util.Optional;

public interface IJsonDtoBindMapper {

//    JsonObject bindObject();

    Optional<String> get(String field);

}
