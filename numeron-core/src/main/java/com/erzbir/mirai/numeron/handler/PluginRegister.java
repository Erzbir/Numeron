package com.erzbir.mirai.numeron.handler;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:15
 * <p>
 * 插件接口, 此项目提供插件模式, 实现此接口即可, 未来打算兼容mirai社区的插件
 * </p>
 */
public interface PluginRegister {
    void register(Bot bot, EventChannel<BotEvent> channel);
}
