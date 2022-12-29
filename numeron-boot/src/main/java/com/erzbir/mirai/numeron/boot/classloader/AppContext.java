package com.erzbir.mirai.numeron.boot.classloader;

import com.erzbir.mirai.numeron.handler.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/12 15:14
 */
@SuppressWarnings("unchecked")
public class AppContext implements BeanFactory {
    public static final AppContext INSTANT = new AppContext();
    private static final String packageName = "com.erzbir.mirai.numeron";
    private final HashMap<String, Object> context = new HashMap<>();

    private AppContext() {
        try {
            ClassScanner scanner = new ClassScanner(packageName, true, s -> true, s -> true);
            Set<Class<Object>> classes = scanner.scanWithAnnotation(Component.class);
            classes.forEach(e -> {
                try {
                    if (!e.isAnnotation() && !e.isEnum() && !e.isInterface()) {
                        Constructor<Object> constructor = e.getConstructor();
                        constructor.setAccessible(true);
                        context.put(e.getSimpleName(), constructor.newInstance());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ex) {
                    ex.printStackTrace();
                }
            });
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public <T> T getBean(Class<T> requiredType) {
        return getBean(requiredType.getSimpleName());
    }

    @Override
    public <T> T getBean(String name) {
        return (T) context.get(name);
    }

    @Override
    public boolean containsBean(String name) {
        return context.containsKey(name);
    }

    public <T> Map<String, T> getBeanWithInter(Class<T> interfaceType) {
        HashMap<String, T> beans = new HashMap<>();
        context.forEach((k, v) -> {
            if (interfaceType.isAssignableFrom(v.getClass())) {
                beans.put(k, (T) v);
            }
        });
        return beans;
    }

    @Override
    public <T> Map<String, T> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        HashMap<String, T> beans = new HashMap<>();
        context.forEach((k, v) -> {
            if (v.getClass().getAnnotation(annotationType) != null) {
                beans.put(k, (T) v);
            }
        });
        return beans;
    }
}