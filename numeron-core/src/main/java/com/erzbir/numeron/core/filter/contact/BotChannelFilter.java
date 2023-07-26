package com.erzbir.numeron.core.filter.contact;

import com.erzbir.numeron.core.filter.AbstractBotEventChannelFilter;
import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:12
 */
public class BotChannelFilter extends AbstractBotEventChannelFilter implements ChannelFilter<BotEvent> {
    @Override
    public boolean filter(BotEvent event) {
        return event.getBot().getId() == botId;
    }
}