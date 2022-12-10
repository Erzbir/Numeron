package com.erzbir.mirai.numeron.configs.controller;

import com.erzbir.mirai.numeron.configs.BotConfig;
import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import com.erzbir.mirai.numeron.processor.Command;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 09:26
 * <p>
 * 开关机命令
 * </p>
 */
@Listener
@Plugin
@SuppressWarnings("unused")
public class OnOffCommandHandle implements PluginRegister {

    @Command(name = "开关机", dec = "关机", help = "/shutdown")
    @Message(text = "/shutdown", filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    private void shutdown(MessageEvent e) {
        e.getSubject().sendMessage("已关机");
        GlobalConfig.isOn = false;
    }

    @Command(name = "开关机", dec = "开机", help = "/launch")
    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, event -> {
            if (event.getMessage().contentToString().equals("/launch") && BotConfig.isMaster(event.getSender().getId())) {
                GlobalConfig.isOn = true;
                event.getSubject().sendMessage("已开机");
            }
        });
    }
}
