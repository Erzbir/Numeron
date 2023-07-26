package com.erzbir.numeron.core.filter.annotation;

import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.core.filter.ChannelFilter;
import com.erzbir.numeron.core.filter.message.MessageEnumFilterFactory;
import com.erzbir.numeron.core.filter.permission.PermissionEnumFilterFactory;
import com.erzbir.numeron.core.filter.rule.RuleEnumFilterFactory;
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
        ChannelFilter<MessageEvent> messageFilter = MessageEnumFilterFactory.INSTANCE.create(messageRule).setText(text).setId(id);
        ChannelFilter<MessageEvent> permissionFilter = PermissionEnumFilterFactory.INSTANCE.create(permission).setId(id);
        ChannelFilter<MessageEvent> ruleFilter = RuleEnumFilterFactory.INSTANCE.create(filterRule).setId(id);
        if (id == 2978086497L) {
            System.err.println(annotation);
            System.err.println(messageRule);
            System.err.println(messageFilter.filter(event) + " " + permissionFilter.filter(event) + " " + ruleFilter.filter(event));
        }
        return (messageFilter.filter(event) && permissionFilter.filter(event) && ruleFilter.filter(event));
    }
}
