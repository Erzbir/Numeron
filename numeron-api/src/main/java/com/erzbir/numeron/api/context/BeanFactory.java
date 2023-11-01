package com.erzbir.numeron.api.context;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * bean 工厂, 定义 bean 的创建和获取
 *
 * @author Erzbir
 * @Date: 2022/12/12 15:16
 */
public interface BeanFactory {

    Object getBean(Class<?> requiredType);

    Object getBean(String name);

    boolean containsBean(String name);

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType);

    Map<String, Object> getBeansWithInter(Class<?> interfaceType);

    default <T> T create(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> constructor = clazz.getConstructor();
        constructor.setAccessible(true);
        return constructor.newInstance();
    }
}
