package com.erzbir.mirai.numeron.filter;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.filter.message.MessageFilterFactory;
import com.erzbir.mirai.numeron.filter.permission.PermissionFilterFactory;
import com.erzbir.mirai.numeron.filter.rule.RuleFilterFactory;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/11/28 13:13
 */
public class MessageChannelFilter {
    public static MessageChannelFilter INSTANCE = new MessageChannelFilter();

    private MessageChannelFilter() {

    }

    public Boolean filter(BotEvent event, Annotation annotation) {
        Class<? extends Annotation> aClass = annotation.annotationType();
        if (aClass != null && event instanceof MessageEvent event1) {
            boolean flag = GlobalConfig.isOn;
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
            ChannelFilterInter channelFilter1 = RuleFilterFactory.INSTANCE.create(filterRule);
            ChannelFilterInter channelFilter2 = MessageFilterFactory.INSTANCE.create(messageRule);
            ChannelFilterInter channelFilter3 = PermissionFilterFactory.INSTANCE.create(permission);
            if (event1 instanceof GroupMessageEvent event2) {
                return flag && channelFilter1.filter(event2, text)
                        && channelFilter2.filter(event2, text)
                        && channelFilter3.filter(event2, text);
            } else {
                return flag && channelFilter2.filter(event1, text)
                        && channelFilter3.filter(event1, text);
            }
        }
        return false;
    }
}
