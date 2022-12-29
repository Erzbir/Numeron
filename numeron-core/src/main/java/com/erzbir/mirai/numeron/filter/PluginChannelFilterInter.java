package com.erzbir.mirai.numeron.filter;

import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 00:49
 */
public interface PluginChannelFilterInter {
    Boolean filter(BotEvent event);
}
