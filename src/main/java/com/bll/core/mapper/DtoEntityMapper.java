package com.bll.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class DtoEntityMapper implements IDtoEntityMapper {

    private final IJsonDtoBindMapper jsonDtoBindMapper = new JsonDtoBindMapper("appUserDtoBind.json");

    @Override
    public <E, R> void mapDtoToEntity(E dtoObject, R entityObject) {
        Field[] dtoFields = dtoObject.getClass().getDeclaredFields();
        Field[] classFields = entityObject.getClass().getDeclaredFields();
        Arrays.stream(classFields).forEach(classField -> {
            AtomicReference<Object> innerObject = new AtomicReference<>(null);
            Arrays.stream(dtoFields).forEach(dtoField -> {
                if (dtoField.getName().equals(classField.getName())
                        && dtoField.getType() == classField.getType()) {
                    mapDtoToObject(dtoObject, entityObject, dtoField);
                }
                if (!typeMap().containsKey(classField.getType().getName())
                        && !classField.getType().isEnum()) {
                    classField.setAccessible(true);
                    Class<?> objectType = classField.getType();
                    Object object;
                    try {
                        object = objectType.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                    Field[] innerObjectFieldArray = object.getClass().getDeclaredFields();
                    Arrays.stream(innerObjectFieldArray).forEach(innerObjectField -> {
                        if (dtoField.getName().equals(innerObjectField.getName())
                                && dtoField.getType() == innerObjectField.getType()) {
                            mapDtoToObject(dtoObject, object, dtoField);
                            innerObject.set(object);
                        }
                    });

                }

            });
            if (innerObject.get() != null && classField.getType().getName().equals(innerObject.get().getClass().getName())
                    && classField.getType() == innerObject.get().getClass()) {
                String setterMethodName = getSetMethodName(classField.getName());
                try {
                    Method setterMethod = entityObject.getClass().getMethod(setterMethodName, classField.getType());
                    classField.setAccessible(true);
                    setterMethod.invoke(entityObject, innerObject.get());
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private <E, R> void mapDtoToObject(E dtoObject, R entityObject, Field dtoField) {
        String setterMethodName = getSetMethodName(dtoField.getName());
        try {
            Method setterMethod = entityObject.getClass().getMethod(setterMethodName, dtoField.getType());
            dtoField.setAccessible(true);
            Object value = dtoField.get(dtoObject);
            setterMethod.invoke(entityObject, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSetMethodName(String input) {
        return "set" + input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private Map<String, String> typeMap() {
        return new HashMap<>() {{
            put("java.lang.String", "");
            put("java.lang.Enum", "");
            put("int", "");
            put("java.lang.Integer", "");
            put("long", "");
            put("java.lang.Long", "");
            put("double", "");
            put("java.lang.Double", "");
        }};
    }
}
