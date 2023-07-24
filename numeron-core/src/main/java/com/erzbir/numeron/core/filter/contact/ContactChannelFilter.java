package com.erzbir.numeron.core.filter.contact;

import com.erzbir.numeron.core.filter.AbstractMessageEventChannelFilter;
import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:08
 */
public class ContactChannelFilter extends AbstractMessageEventChannelFilter implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        return id == 0 || id == event.getSender().getId();
    }
}
