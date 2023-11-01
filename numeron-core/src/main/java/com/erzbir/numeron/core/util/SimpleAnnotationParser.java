package com.erzbir.numeron.core.util;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class SimpleAnnotationParser implements AnnotationParser {
    private final String[] keys;
    private final Annotation annotation;
    @Getter
    private final Map<String, Object> resultMap = new HashMap<>();

    public SimpleAnnotationParser(Annotation annotation, String... keys) {
        this.annotation = annotation;
        this.keys = keys;
    }


    @Override
    public void inject(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends Annotation> aClass = annotation.getClass();
        for (String key : keys) {
            Method declaredMethod = aClass.getDeclaredMethod(key);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(annotation);
            resultMap.put(key, invoke);
        }
    }

    @Override
    public Object get(String key) {
        return resultMap.get(key);
    }
}
