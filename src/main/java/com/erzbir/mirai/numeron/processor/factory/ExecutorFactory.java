package com.erzbir.mirai.numeron.processor.factory;

import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;
import com.erzbir.mirai.numeron.listener.other.GroupMember;
import com.erzbir.mirai.numeron.listener.other.UserAction;
import com.erzbir.mirai.numeron.processor.entiry.executor.*;

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
        if (annotation instanceof GroupMessage) {
            return GroupMessageMethodExecutor.INSTANCE;
        } else if (annotation instanceof UserMessage) {
            return UserMessageMethodExecutor.INSTANCE;
        } else if (annotation instanceof Message) {
            return MessageMethodExecutor.INSTANCE;
        } else if (annotation instanceof GroupMember) {
            return MemberEventMethodExecutor.INSTANCE;
        } else if (annotation instanceof UserAction) {
            return UserEventMethodExecutor.INSTANCE;
        }
        return null;
    }
}
