package com.bll.core.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonDtoBindMapper implements IJsonDtoBindMapper {

    private final Gson gson = new Gson();

    private final String jsonName;

    public JsonDtoBindMapper(String jsonName) {
        this.jsonName = jsonName;
    }

    public JsonObject bindObject(String jsonName) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(jsonName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return gson.fromJson(reader, JsonObject.class);
    }

}
