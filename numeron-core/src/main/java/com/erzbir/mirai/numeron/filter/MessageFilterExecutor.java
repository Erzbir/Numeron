package com.erzbir.mirai.numeron.filter;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.filter.message.MessageFilterFactory;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionFilterFactory;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.filter.rule.RuleFilterFactory;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/11/28 13:13
 * 消息事件过滤类, 这个类计划作为一个子类
 */
public class MessageFilterExecutor {
    public final static MessageFilterExecutor INSTANCE = new MessageFilterExecutor();

    private MessageFilterExecutor() {

    }

    public Boolean filter(BotEvent event, Annotation annotation) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        MessageEvent event1 = (MessageEvent) event;
        FilterRule filterRule;
        MessageRule messageRule;
        String text;
        PermissionType permission;
        try {
            // 以下操作通过反射调用注解的方法, 再强制类型转换成对应的类型
            filterRule = (FilterRule) aClass.getDeclaredMethod("filterRule").invoke(annotation);
            messageRule = (MessageRule) aClass.getDeclaredMethod("messageRule").invoke(annotation);
            text = (String) aClass.getDeclaredMethod("text").invoke(annotation);
            permission = (PermissionType) aClass.getDeclaredMethod("permission").invoke(annotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return NumeronBot.INSTANCE.isOpen() && RuleFilterFactory.INSTANCE.create(filterRule).filter(event1, text)
                && MessageFilterFactory.INSTANCE.create(messageRule).filter(event1, text)
                && PermissionFilterFactory.INSTANCE.create(permission).filter(event1, text);
    }
}
