package com.erzbir.mirai.numeron.filter;

import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:18
 * <p>
 * 插件过滤器接口
 * </p>
 */
public interface ChannelFilterInter {
    Boolean filter(BotEvent event);
}
