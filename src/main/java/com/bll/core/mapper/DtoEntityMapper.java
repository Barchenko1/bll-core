package com.bll.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DtoEntityMapper implements IDtoEntityMapper {

    private final IJsonDtoBindMapper jsonDtoBindMapper;

    public DtoEntityMapper(IJsonDtoBindMapper jsonDtoBindMapper) {
        this.jsonDtoBindMapper = jsonDtoBindMapper;
    }

    @Override
    public <E, R> void mapDtoToEntity(E dtoObject, R entityObject) {
        Field[] dtoFields = dtoObject.getClass().getDeclaredFields();
        Field[] classFields = entityObject.getClass().getDeclaredFields();

        for (Field classField: classFields) {
            Map<Field, Object> tempFieldValueMap = new HashMap<>();
            for (Field dtoField: dtoFields) {
                if (dtoField.getName().equals(classField.getName())
                        && dtoField.getType() == classField.getType()) {
                    mapDtoToObject(dtoObject, entityObject, dtoField);
                }
                if (isClassBind(classField, dtoField)) {
                    mapFieldValue(dtoObject, classField, dtoField, tempFieldValueMap);
                }
            }
            if (!tempFieldValueMap.isEmpty()) {
                fillInnerClassFields(entityObject, classField, tempFieldValueMap);
            }

        }
    }

    private <E> void mapFieldValue(E dtoObject, Field classField, Field dtoField, Map<Field, Object> tempFildValueMap) {
        String classFieldName = getBindFieldName(dtoField);
        Object object = getInnerObject(classField);
        Field[] innerObjectFieldArray = object.getClass().getDeclaredFields();
        Arrays.stream(innerObjectFieldArray)
                .filter(field -> field.getName().equals(classFieldName))
                .findFirst()
                .ifPresent(field -> {
                    dtoField.setAccessible(true);
                    try {
                        tempFildValueMap.put(dtoField, dtoField.get(dtoObject));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private <R> void fillInnerClassFields(R entityObject, Field classField, Map<Field, Object> tempFildValueMap) {
        Object object = getInnerObject(classField);
        tempFildValueMap.forEach((key, value) -> {
            setField(object, key, value);
        });
        setObject(entityObject, classField, object);
    }

    private <E, R> void mapDtoToObject(E dtoObject, R entityObject, Field dtoField) {
        String setterMethodName = getBindSetterName(dtoField);
        try {
            Method setterMethod = entityObject.getClass().getMethod(setterMethodName, dtoField.getType());
            dtoField.setAccessible(true);
            Object value = dtoField.get(dtoObject);
            setterMethod.invoke(entityObject, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private String getBindSetterName(Field dtoField) {
        return jsonDtoBindMapper.get(dtoField.getName())
                .map(name -> getSetMethodName(name.substring(name.lastIndexOf(".") + 1)))
                .orElseThrow(() -> new RuntimeException("wrong json bind mapping"));
    }

    private boolean isClassBind(Field classField, Field dtoField) {
        return jsonDtoBindMapper.get(dtoField.getName())
                .filter(name -> name.contains("."))
                .map(name -> name.substring(0, name.lastIndexOf(".")))
                .filter(name-> name.equalsIgnoreCase(classField.getName()))
                .isPresent();
    }

    private String getBindFieldName(Field dtoField) {
        return jsonDtoBindMapper.get(dtoField.getName())
                .map(name -> name.substring(name.lastIndexOf(".") + 1))
                .orElseThrow(() -> new RuntimeException("wrong json bind mapping"));
    }

    private String getSetMethodName(String input) {
        return "set" + input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    private Object getInnerObject(Field field) {
        try {
            field.setAccessible(true);
            return field.getType().getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private void setField(Object entityObject, Field field, Object value) {
        String setterMethodName = getBindSetterName(field);
        try {
            Method setterMethod = entityObject.getClass().getMethod(setterMethodName, field.getType());
            field.setAccessible(true);
            setterMethod.invoke(entityObject, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private <R> void setObject(R entityObject, Field classField, Object object) {
        String setterMethodName = getSetMethodName(classField.getName());
        try {
            Method setterMethod = entityObject.getClass().getMethod(setterMethodName, classField.getType());
            classField.setAccessible(true);
            setterMethod.invoke(entityObject, object);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
