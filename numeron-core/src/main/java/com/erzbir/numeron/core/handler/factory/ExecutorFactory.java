package com.erzbir.numeron.core.handler.factory;

import com.erzbir.numeron.core.handler.Event;
import com.erzbir.numeron.core.handler.Message;
import com.erzbir.numeron.core.handler.excute.EventMethodExecute;
import com.erzbir.numeron.core.handler.executor.AbstractMethodExecutor;
import com.erzbir.numeron.core.handler.executor.MethodExecutor;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2022/11/28 01:03
 * <p>处理器工厂</p>
 */
public class ExecutorFactory implements AbstractFactory {
    public final static ExecutorFactory INSTANCE = new ExecutorFactory();

    private ExecutorFactory() {

    }

    /**
     * 这个方法根据注解的类型生产出不同功能的处理器
     *
     * @param annotation 注解
     * @return 返回一个处理器
     */
    @Override
    public AbstractMethodExecutor create(Annotation annotation) {
        MethodExecutor executor = MethodExecutor.INSTANCE;
        if (annotation instanceof Message || annotation instanceof Event) {
            executor.setExecute(EventMethodExecute.INSTANCE);
            return executor;
        }
        return null;
    }
}
