package com.erzbir.mirai.numeron.Interface;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @Author: Erzbir
 * @Date: 2022/11/16 22:15
 */
public interface PluginRegister {
    void register(Bot bot, EventChannel<BotEvent> channel);
}
