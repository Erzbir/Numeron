package com.erzbir.mirai.numeron.Interface;

import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:18
 */

public interface ChannelFilterInter {
    Boolean filter(BotEvent event);
}
