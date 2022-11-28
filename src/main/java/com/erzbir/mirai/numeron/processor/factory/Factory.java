package com.erzbir.mirai.numeron.processor.factory;

import com.erzbir.mirai.numeron.processor.entiry.executor.AbstractMethodExecutor;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2022/11/28 01:05
 */
public interface Factory {
    AbstractMethodExecutor create(Annotation annotation);
}
