package com.erzbir.numeron.api.context;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2023/6/17 23:24
 */
public interface AppContextService {
    void addAllToContext(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation);

    void addToContext(Class<?> bean);
}
