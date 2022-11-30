package com.erzbir.mirai.numeron.processor.factory;

import com.erzbir.mirai.numeron.processor.executor.AbstractMethodExecutor;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2022/11/28 01:05
 */
public interface AbstractFactory {
    AbstractMethodExecutor create(Annotation annotation);
}
