package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 09:26
 */
@Listener
@SuppressWarnings("unused")
public class OnOffCommandHandle implements PluginRegister {

    @Message(text = "/shutdown", filterRule = FilterRule.NONE, permission = PermissionType.MASTER)
    public void shutdown(MessageEvent e) {
        e.getSubject().sendMessage("已关机");
        GlobalConfig.isOn = false;
    }

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, event -> {
            if (event.getMessage().contentToString().equals("/launch") && event.getSender().getId() == GlobalConfig.master) {
                GlobalConfig.isOn = true;
                event.getSubject().sendMessage("已开机");
            }
        });
    }
}
