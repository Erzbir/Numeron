package com.erzbir.numeron.core.filter.annotation;

import com.erzbir.numeron.annotation.ListenTarget;
import com.erzbir.numeron.core.filter.ChannelFilter;
import com.erzbir.numeron.core.filter.contact.BotChannelFilter;
import com.erzbir.numeron.core.filter.contact.ContactChannelFilter;
import com.erzbir.numeron.filter.TargetType;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;


/**
 * @author Erzbir
 * @Date: 2023/7/5 12:03
 */
public class TargetAnnotationChannelFilter extends AbstractAnnotationChannelFilter<ListenTarget, BotEvent> implements ChannelFilter<BotEvent> {
    @Override
    public boolean filter(BotEvent event) {
        long id = annotation.id();
        TargetType targetType = annotation.value();
        if (id == 0) {
            return true;
        }
        if (targetType.equals(TargetType.BOT)) {
            BotChannelFilter botFilter = new BotChannelFilter();
            botFilter.setBotId(id);
            return botFilter.filter(event);
        } else {
            if (event instanceof MessageEvent event1) {
                ContactChannelFilter contactFilter = new ContactChannelFilter();
                contactFilter.setId(id);
                return contactFilter.filter(event1);
            }
        }
        return true;
    }
}
