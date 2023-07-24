package com.erzbir.numeron.core.filter.annotation;

import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.core.filter.ChannelFilter;
import com.erzbir.numeron.core.filter.message.MessageCacheFilterFactory;
import com.erzbir.numeron.core.filter.permission.PermissionCacheFilterFactory;
import com.erzbir.numeron.core.filter.rule.RuleCacheFilterFactory;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/30 17:37
 */
public class MessageAnnotationChannelFilter extends AbstractAnnotationChannelFilter<Message, MessageEvent> implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        FilterRule filterRule = annotation.filterRule();
        MessageRule messageRule = annotation.messageRule();
        String text = annotation.text();
        PermissionType permission = annotation.permission();
        long id = event.getSender().getId();
        ChannelFilter<MessageEvent> messageFilter = MessageCacheFilterFactory.INSTANCE.create(messageRule).setText(text).setId(id);
        ChannelFilter<MessageEvent> permissionFilter = PermissionCacheFilterFactory.INSTANCE.create(permission).setId(id);
        ChannelFilter<MessageEvent> ruleFilter = RuleCacheFilterFactory.INSTANCE.create(filterRule).setId(id);
        return messageFilter.filter(event) && permissionFilter.filter(event) && ruleFilter.filter(event);
    }
}
