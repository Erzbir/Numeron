package com.erzbir.mirai.numeron.handler.factory;

import com.erzbir.mirai.numeron.handler.excute.GroupMessageMethodExecute;
import com.erzbir.mirai.numeron.handler.excute.MessageMethodExecute;
import com.erzbir.mirai.numeron.handler.excute.UserMessageMethodExecute;
import com.erzbir.mirai.numeron.handler.executor.AbstractMethodExecutor;
import com.erzbir.mirai.numeron.handler.executor.MethodExecutor;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;

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
        if (annotation instanceof GroupMessage) {
            executor.setExecute(GroupMessageMethodExecute.INSTANCE);
            return executor;
        } else if (annotation instanceof UserMessage) {
            executor.setExecute(UserMessageMethodExecute.INSTANCE);
            return executor;
        } else if (annotation instanceof Message) {
            executor.setExecute(MessageMethodExecute.INSTANCE);
            return executor;
        }
        return null;
    }
}
