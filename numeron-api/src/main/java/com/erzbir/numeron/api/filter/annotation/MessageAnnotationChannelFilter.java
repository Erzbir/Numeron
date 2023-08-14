package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.MessageFilter;
import com.erzbir.numeron.api.filter.ChannelFilter;
import com.erzbir.numeron.api.filter.CustomFilter;
import com.erzbir.numeron.api.filter.DefaultFilter;
import com.erzbir.numeron.api.filter.factory.message.MessageEnumFilterFactory;
import com.erzbir.numeron.api.filter.factory.permission.PermissionEnumFilterFactory;
import com.erzbir.numeron.api.filter.factory.rule.RuleEnumFilterFactory;
import com.erzbir.numeron.api.filter.target.TargetMessageChannelFilter;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.events.MessageEvent;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 针对方法上的 {@link MessageFilter} 注解解析过滤 channel
 * </p>
 * <p>
 * 默认使用非缓存的工厂进行创建, 原因是多线程不安全
 * </p>
 *
 * @author Erzbir
 * @Date: 2023/6/30 17:37
 */
public class MessageAnnotationChannelFilter extends AbstractAnnotationChannelFilter<MessageFilter, MessageEvent> implements ChannelFilter<MessageEvent> {
    private final MessageEnumFilterFactory messageEnumFilterFactory = MessageEnumFilterFactory.INSTANCE;
    private final PermissionEnumFilterFactory permissionEnumFilterFactory = PermissionEnumFilterFactory.INSTANCE;
    private final RuleEnumFilterFactory ruleEnumFilterFactory = RuleEnumFilterFactory.INSTANCE;


    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean filter(MessageEvent event) {
        FilterRule filterRule = annotation.filterRule();
        MessageRule messageRule = annotation.messageRule();
        String text = annotation.text();
        long target = annotation.id();
        PermissionType permission = annotation.permission();
        Class<? extends CustomFilter<?>> filter = annotation.customFilter();
        long id = event.getSender().getId();
        ChannelFilter<MessageEvent> messageFilter = messageEnumFilterFactory.create(messageRule).setText(text).setId(id);
        ChannelFilter<MessageEvent> permissionFilter = permissionEnumFilterFactory.create(permission).setId(id);
        ChannelFilter<MessageEvent> ruleFilter = ruleEnumFilterFactory.create(filterRule).setId(id);
        ChannelFilter<MessageEvent> targetFilter = new TargetMessageChannelFilter().setId(target);
        if (filterRule.equals(FilterRule.CUSTOM) && !filter.equals(DefaultFilter.class)) {
            try {
                CustomFilter customFilter = filter.getConstructor().newInstance();
                return customFilter.filter(event) && messageFilter.filter(event) && permissionFilter.filter(event) && targetFilter.filter(event);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                NumeronLogUtil.logger.error(e.getMessage(), e);
            }
        }
        return messageFilter.filter(event) && permissionFilter.filter(event) && ruleFilter.filter(event) && targetFilter.filter(event);
    }
}
