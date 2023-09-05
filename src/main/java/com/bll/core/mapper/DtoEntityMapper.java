package com.bll.core.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DtoEntityMapper implements IDtoEntityMapper {

    private final IJsonDtoBindMapper jsonDtoBindMapper = new JsonDtoBindMapper("appUserDtoBind.json");

    @Override
    public <E, R> void mapDtoToEntity(E dtoObject, R entityObject) {
        Field[] dtoFields = dtoObject.getClass().getDeclaredFields();
        Field[] classFields = entityObject.getClass().getDeclaredFields();

        for (Field classField: classFields) {
            Map<Field, Object> tempFildValueMap = new HashMap<>();
            for (Field dtoField: dtoFields) {
                if (dtoField.getName().equals(classField.getName())
                        && dtoField.getType() == classField.getType()) {
                    mapDtoToObject(dtoObject, entityObject, dtoField);
                }
                if (!typeSet().contains(classField.getType().getName())) {
                    Object object = getInnerObject(classField);
                    Field[] innerObjectFieldArray = object.getClass().getDeclaredFields();
                    Optional<Field> innerObjectFieldOpt = findMatchingDtoField(innerObjectFieldArray, dtoField);
                    innerObjectFieldOpt.ifPresent(innerObjectField -> {
                        mapDtoToObject(dtoObject, object, dtoField);
                        try {
                            tempFildValueMap.put(dtoField, dtoField.get(dtoObject));
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            if (!tempFildValueMap.isEmpty()) {
                Object object = getInnerObject(classField);
                tempFildValueMap.forEach((key, value) -> {
                    setField(object, key, value);
                });
                setField(entityObject, classField, object);
            }

        }
    }

    private Optional<Field> findMatchingDtoField(Field[] dtoFields, Field classField) {
        return Arrays.stream(dtoFields)
                .filter(dtoField -> dtoField.getName().equals(classField.getName())
                        && dtoField.getType() == classField.getType())
                .findFirst();
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
        String setterMethodName = getSetMethodName(field.getName());
        try {
            Method setterMethod = entityObject.getClass().getMethod(setterMethodName, field.getType());
            field.setAccessible(true);
            setterMethod.invoke(entityObject, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> typeSet() {
        return new HashSet<>() {{
            add("java.lang.String");
            add("java.lang.Enum");
            add("char");
            add("java.lang.Char");
            add("byte");
            add("java.lang.Byte");
            add("short");
            add("java.lang.Short");
            add("int");
            add("java.lang.Integer");
            add("long");
            add("java.lang.Long");
            add("float");
            add("java.lang.Float");
            add("double");
            add("java.lang.Double");
            add("java.lang.Boolean");
            add("boolean");
        }};
    }
}
