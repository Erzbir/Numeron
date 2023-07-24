package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.events.BotEvent;

/**
 * bot 事件过滤抽象类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:41
 * @see AbstractEventChannelFilter
 */
public abstract class AbstractBotEventChannelFilter extends AbstractEventChannelFilter<BotEvent> implements ChannelFilter<BotEvent> {
    protected long botId = 0;

    public long getBotId() {
        return botId;
    }

    public void setBotId(long botId) {
        this.botId = botId;
    }
}
