package com.erzbir.numeron.api.context;

import com.erzbir.numeron.utils.NumeronLogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * bean 工厂, 定义 bean 的创建和获取
 *
 * @author Erzbir
 * @Date: 2022/12/12 15:16
 */
public interface BeanFactory extends Context {

    Object getBean(Class<?> requiredType);

    Object getBean(String name);

    Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType);

    Map<String, Object> getBeansWithInter(Class<?> interfaceType);

    void plus(Class<?> c);

    void plus(Set<Class<?>> classes);

    void plus(Set<Class<?>> classes, Predicate<Class<?>> filter);

    void plus(String packageName, ClassLoader classLoader);

    void plus(String packageName, ClassLoader classLoader, Predicate<Class<?>> filter);

    void plus(String name, Class<?> c);

    void plusByAnnotation(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation);

    void plusBySupperClass(String packageName, ClassLoader classLoader, Class<?> supperClass);

    Class<?> reduce(String name);

    Class<?> reduce(Class<?> c);

    Class<?> get(String name);

    default <T> T create(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            NumeronLogUtil.err(e.getMessage());
            throw new RuntimeException("bean create exception");
        }
    }

}
