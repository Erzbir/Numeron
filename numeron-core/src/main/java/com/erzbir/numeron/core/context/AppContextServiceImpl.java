package com.erzbir.numeron.core.context;

import com.erzbir.numeron.api.context.AppContextService;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author Erzbir
 * @Date: 2023/6/17 23:27
 */
public class AppContextServiceImpl implements AppContextService {
    @Override
    public void addAllToContext(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation) {
        AppContext.INSTANCE.addAllToContext(packageName, classLoader, annotation);
    }

    @Override
    public void addAllToContext(Collection<Class<?>> classes) {
        classes.forEach(this::addToContext);
    }

    @Override
    public void addToContext(Class<?> bean) {
        AppContext.INSTANCE.addBean(bean);
    }

    @Override
    public void addBean(Object bean) {
        AppContext.INSTANCE.addBean(bean);
    }

    @Override
    public Object getBean(Class<?> requiredType) {
        return getBean(requiredType.getName());
    }

    @Override
    public Object getBean(String name) {
        return AppContext.INSTANCE.getBean(name);
    }

    @Override
    public void removeBean(String name) {
        AppContext.INSTANCE.removeBean(name);
    }

    @Override
    public void removeBean(Class<?> c) {
        AppContext.INSTANCE.removeBean(c);
    }
}
