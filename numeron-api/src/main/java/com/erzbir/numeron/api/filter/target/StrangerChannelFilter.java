package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.StrangerEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class StrangerChannelFilter extends AbstractContactChannelFilter<StrangerEvent> implements ChannelFilter<StrangerEvent> {
    @Override
    public boolean filter(StrangerEvent event) {
        return event.getStranger().getId() == id || id == 0;
    }
}
