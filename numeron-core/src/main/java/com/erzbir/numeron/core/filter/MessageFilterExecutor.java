package com.erzbir.numeron.core.filter;

import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.exception.AnnotationGetException;
import com.erzbir.numeron.core.filter.message.MessageFilterFactory;
import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionFilterFactory;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.filter.rule.RuleFilterFactory;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/11/28 13:13
 * <p>消息事件过滤类, 这个类计划作为一个子类</p>
 */
public class MessageFilterExecutor {
    public final static MessageFilterExecutor INSTANCE = new MessageFilterExecutor();

    private MessageFilterExecutor() {

    }

    public Boolean filter(Event event, Annotation annotation) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        MessageEvent event1 = (MessageEvent) event;
        FilterRule filterRule;
        MessageRule messageRule;
        String text;
        PermissionType permission;
        try {
            // 以下操作通过反射调用注解的方法(注解的属性实际上是一个有返回值的方法), 再强制类型转换成对应的类型
            filterRule = (FilterRule) aClass.getDeclaredMethod("filterRule").invoke(annotation);
            messageRule = (MessageRule) aClass.getDeclaredMethod("messageRule").invoke(annotation);
            text = (String) aClass.getDeclaredMethod("text").invoke(annotation);
            permission = (PermissionType) aClass.getDeclaredMethod("permission").invoke(annotation);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new AnnotationGetException(e);
        }
        return NumeronBot.INSTANCE.isEnable() && RuleFilterFactory.INSTANCE.create(filterRule).filter(event1, text)
                && MessageFilterFactory.INSTANCE.create(messageRule).filter(event1, text)
                && PermissionFilterFactory.INSTANCE.create(permission).filter(event1, text);
    }
}
