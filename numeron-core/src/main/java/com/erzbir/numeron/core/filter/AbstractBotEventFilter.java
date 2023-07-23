package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * bot 事件过滤抽象装饰类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:41
 * @see AbstractEventFilter
 * @see BotEvent
 */
public abstract class AbstractBotEventFilter implements Filter<BotEvent> {
    protected long botId = 0;

    public abstract EventChannel<? extends BotEvent> filter(EventChannel<? extends BotEvent> channel);

    public long getBotId() {
        return botId;
    }

    public void setBotId(long botId) {
        this.botId = botId;
    }
}
