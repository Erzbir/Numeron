package com.erzbir.numeron.core.util;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public interface AnnotationParser {
    void inject(Object object);

    Object get(String key);
}
