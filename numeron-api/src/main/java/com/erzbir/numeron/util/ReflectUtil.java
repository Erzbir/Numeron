package com.erzbir.numeron.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Erzbir
 * @Data: 2024/2/11 14:21
 */
public class ReflectUtil {
    public static Class<?> getGenericActualType(Object object) {
        Type genericSuperclass = object.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType parameterizedType) {
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }
        return Object.class;
    }
}
