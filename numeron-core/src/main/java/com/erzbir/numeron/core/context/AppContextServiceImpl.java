package com.erzbir.numeron.core.context;

import com.erzbir.numeron.api.context.AppContextService;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
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
        try {
            AppContext.INSTANCE.addToContext(bean);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            NumeronLogUtil.logger.error("ERROR", e);
        }
    }

    @Override
    public void removeContext(String name) {
        AppContext.INSTANCE.removeBean(name);
    }

    @Override
    public void removeContext(Class<?> c) {
        AppContext.INSTANCE.removeBean(c);
    }
}
