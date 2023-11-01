package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.AbstractBotEventChannelFilter;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public abstract class AbstractContactChannelFilter<E extends BotEvent> extends AbstractBotEventChannelFilter<E> implements ChannelFilter<E> {
    protected long id = 0;

    public long getId() {
        return id;
    }

    public AbstractContactChannelFilter<E> setId(long id) {
        this.id = id;
        return this;
    }

    public abstract boolean filter(E event);
}
