package com.erzbir.numeron.core.filter.event.contact;

import com.erzbir.numeron.core.filter.AbstractMessageEventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:08
 */
public class ContactFilter extends AbstractMessageEventFilter implements Filter<MessageEvent> {
    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        return channel.filter(event -> id == 0 || id == event.getSender().getId());
    }
}
