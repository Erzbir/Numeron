package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.events.BotEvent;

/**
 * bot 事件过滤抽象装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:41
 * @see AbstractEventFilter
 * @see EventFilter
 * @see BotEvent
 */
public abstract class AbstractBotEventFilter<E extends BotEvent> extends AbstractEventFilter<E> implements EventFilter<E> {
    protected long botId = 0;

    public AbstractBotEventFilter(Filter filter) {
        super(filter);
    }

    public long getBotId() {
        return botId;
    }

    public void setBotId(long botId) {
        this.botId = botId;
    }
}
