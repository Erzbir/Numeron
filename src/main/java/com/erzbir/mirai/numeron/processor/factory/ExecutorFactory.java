package com.erzbir.mirai.numeron.processor.factory;

import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;
import com.erzbir.mirai.numeron.processor.entiry.excute.GroupMessageMethodExecute;
import com.erzbir.mirai.numeron.processor.entiry.excute.MessageMethodExecute;
import com.erzbir.mirai.numeron.processor.entiry.excute.UserMessageMethodExecute;
import com.erzbir.mirai.numeron.processor.entiry.executor.AbstractMethodExecutor;
import com.erzbir.mirai.numeron.processor.entiry.executor.MethodExecutor;

import java.lang.annotation.Annotation;

/**
 * @author Erzbir
 * @Date: 2022/11/28 01:03
 */
public class ExecutorFactory implements Factory {
    public static ExecutorFactory INSTANCE = new ExecutorFactory();

    private ExecutorFactory() {

    }

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
