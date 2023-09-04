package com.bll.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DtoEntityMapper implements IDtoEntityMapper {

    @Override
    public <E, T> void mapDtoToEntity(E dtoObject, T entityObject) {
        Field[] dtoFields = dtoObject.getClass().getDeclaredFields();
        Field[] classFields = entityObject.getClass().getDeclaredFields();
        Arrays.stream(dtoFields).forEach(dtoField -> {
            Arrays.stream(classFields)
                    .filter(classField -> dtoField.getName().equals(classField.getName()) && dtoField.getType() == classField.getType())
                    .findFirst()
                    .ifPresent(matchedField -> {
                        String setterMethodName = getSetMethodName(dtoField.getName());
                        try {
                            Method setterMethod = entityObject.getClass().getMethod(setterMethodName, matchedField.getType());
                            dtoField.setAccessible(true);
                            Object value = dtoField.get(dtoObject);
                            setterMethod.invoke(entityObject, value);
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    });
        });
    }

    private String getSetMethodName(String input) {
        return "set" + input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
