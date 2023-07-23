package com.erzbir.numeron.core.filter.event.contact;

import com.erzbir.numeron.core.filter.AbstractBotEventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:12
 */
public class BotFilter extends AbstractBotEventFilter implements Filter<BotEvent> {
    @Override
    public EventChannel<? extends BotEvent> filter(EventChannel<? extends BotEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends BotEvent> filter0(EventChannel<? extends BotEvent> channel) {
        return channel.filter(event -> event.getBot().getId() == botId);
    }
}