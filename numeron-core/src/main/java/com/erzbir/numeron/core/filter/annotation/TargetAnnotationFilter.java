package com.erzbir.numeron.core.filter.annotation;

import com.erzbir.numeron.annotation.ListenTarget;
import com.erzbir.numeron.core.filter.Filter;
import com.erzbir.numeron.core.filter.event.contact.BotFilter;
import com.erzbir.numeron.core.filter.event.contact.ContactFilter;
import com.erzbir.numeron.filter.TargetType;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;


/**
 * @author Erzbir
 * @Date: 2023/7/5 12:03
 */
public class TargetAnnotationFilter extends AbstractAnnotationFilter<ListenTarget, BotEvent> implements Filter<BotEvent> {
    @Override
    public EventChannel<? extends BotEvent> filter(EventChannel<? extends BotEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends BotEvent> filter0(EventChannel<? extends BotEvent> channel) {
        long id = annotation.id();
        TargetType targetType = annotation.value();
        Class<?> aClass = channel.getClass();
        if (targetType.equals(TargetType.BOT) && aClass.getGenericSuperclass() instanceof BotEvent) {
            BotFilter botFilter = new BotFilter();
            botFilter.setBotId(id);
            return botFilter.filter(channel);
        } else {
            ContactFilter contactFilter = new ContactFilter();
            contactFilter.setId(id);
            return contactFilter.filter(channel.filterIsInstance(MessageEvent.class));
        }
    }
}
