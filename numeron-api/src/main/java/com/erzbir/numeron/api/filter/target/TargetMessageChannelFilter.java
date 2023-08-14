package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class TargetMessageChannelFilter extends AbstractContactChannelFilter<MessageEvent> implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        return event.getSender().getId() == id || id == 0;
    }
}
