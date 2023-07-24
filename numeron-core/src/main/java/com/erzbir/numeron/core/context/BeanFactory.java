package com.erzbir.numeron.core.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2022/12/12 15:16
 */
public interface BeanFactory {

    Object getBean(Class<?> requiredType) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Object getBean(String name) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    boolean containsBean(String name);

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType);

    Map<String, Object> getBeansWithInter(Class<?> interfaceType);

    default <T> T create(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return clazz.getConstructor().newInstance();
    }
}
