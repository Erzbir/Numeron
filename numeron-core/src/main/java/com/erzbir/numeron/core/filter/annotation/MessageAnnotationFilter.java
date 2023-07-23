package com.erzbir.numeron.core.filter.annotation;

import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.core.filter.AbstractMessageEventFilter;
import com.erzbir.numeron.core.filter.Filter;
import com.erzbir.numeron.core.filter.event.message.MessageCacheFilterFactory;
import com.erzbir.numeron.core.filter.event.permission.PermissionCacheFilterFactory;
import com.erzbir.numeron.core.filter.event.rule.RuleCacheFilterFactory;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/30 17:37
 */
public class MessageAnnotationFilter extends AbstractAnnotationFilter<Message, MessageEvent> implements Filter<MessageEvent> {

    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    public EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        FilterRule filterRule = annotation.filterRule();
        MessageRule messageRule = annotation.messageRule();
        String text = annotation.text();
        PermissionType permission = annotation.permission();
        channel.filter(event -> {
            long id = event.getSender().getId();
            System.err.println(id);
            AbstractMessageEventFilter messageFilter = MessageCacheFilterFactory.INSTANCE.create(messageRule).setText(text).setId(id);
            AbstractMessageEventFilter permissionFilter = PermissionCacheFilterFactory.INSTANCE.create(permission).setId(id);
            AbstractMessageEventFilter ruleFilter = RuleCacheFilterFactory.INSTANCE.create(filterRule).setId(id);
            return ruleFilter.filter(permissionFilter.filter(messageFilter.filter(channel))) != null;
        });
        return channel;
    }
}
