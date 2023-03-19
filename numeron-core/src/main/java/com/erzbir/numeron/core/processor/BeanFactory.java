package com.erzbir.numeron.core.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2022/12/12 15:16
 */
public interface BeanFactory {

    <T> T getBean(Class<T> requiredType);

    <T> T getBean(String name);

    boolean containsBean(String name);

    <T> Map<String, T> getBeansWithAnnotation(Class<? extends Annotation> annotationType);

    default <T> T create(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return clazz.getConstructor().newInstance();
    }
}
