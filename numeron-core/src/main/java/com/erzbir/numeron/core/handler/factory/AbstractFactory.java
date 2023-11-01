package com.erzbir.numeron.core.handler.factory;

import com.erzbir.numeron.core.handler.executor.AbstractMethodExecutor;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2022/11/28 01:05
 */
@Deprecated
public interface AbstractFactory {
    AbstractMethodExecutor create(Annotation annotation);
}
