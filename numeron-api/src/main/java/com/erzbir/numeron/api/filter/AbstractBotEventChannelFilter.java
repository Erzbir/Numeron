package com.erzbir.numeron.api.filter;

import net.mamoe.mirai.event.events.BotEvent;

/**
 * bot 事件过滤抽象类
 *
 * @author Erzbir
 * @Date: 2023/6/25 15:41
 * @see AbstractEventChannelFilter
 */
public abstract class AbstractBotEventChannelFilter<E extends BotEvent> extends AbstractEventChannelFilter<E> implements ChannelFilter<E> {

}
