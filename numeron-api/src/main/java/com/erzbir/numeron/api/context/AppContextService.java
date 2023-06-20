package com.erzbir.numeron.api.context;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author Erzbir
 * @Date: 2023/6/17 23:24
 */
public interface AppContextService {
    void addAllToContext(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation);

    void addAllToContext(Collection<Class<?>> classes);

    void addToContext(Class<?> bean);

    void removeContext(String name);

    void removeContext(Class<?> c);
}
