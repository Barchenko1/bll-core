package com.bll.core.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

public class JsonDtoBindMapper implements IJsonDtoBindMapper {

    private final Gson gson = new Gson();
    private final JsonObject jsonObject;

    public JsonDtoBindMapper(String jsonName) {
        this.jsonObject = bindObject(jsonName);
    }

    private JsonObject bindObject(String jsonName) {
        BufferedReader reader;
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(jsonName)){
            reader = new BufferedReader(new InputStreamReader(in));
            return gson.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> get(String field) {
        return jsonObject.has(field) ? Optional.of(jsonObject.get(field).getAsString()) : Optional.empty();
    }
}
