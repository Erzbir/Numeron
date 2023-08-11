package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:12
 */
public class BotChannelFilter extends AbstractContactChannelFilter<BotEvent> implements ChannelFilter<BotEvent> {
    @Override
    public boolean filter(BotEvent event) {
        return event.getBot().getId() == id || id == 0;
    }
}