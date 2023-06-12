package com.erzbir.numeron.core.filter.executor;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.core.exception.AnnotationGetException;
import com.erzbir.numeron.core.filter.message.MessageFilterFactory;
import com.erzbir.numeron.core.filter.permission.PermissionFilterFactory;
import com.erzbir.numeron.core.filter.rule.RuleFilterFactory;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/11/28 13:13
 * <p>消息事件过滤类, 这个类计划作为一个子类</p>
 */
public class MessageFilterExecutor implements MessageFilterExecutorInter {
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
            NumeronLogUtil.logger.error(e);
            throw new AnnotationGetException(e);
        }
        return BotServiceImpl.INSTANCE.getConfiguration((event1.getBot().getId())).isEnable() && RuleFilterFactory.INSTANCE.create(filterRule, text).filter(event1)
                && MessageFilterFactory.INSTANCE.create(messageRule, text).filter(event1)
                && PermissionFilterFactory.INSTANCE.create(permission, text).filter(event1);
    }
}
