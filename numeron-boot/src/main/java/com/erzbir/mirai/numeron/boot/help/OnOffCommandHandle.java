package com.erzbir.mirai.numeron.boot.help;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.handler.Plugin;
import com.erzbir.mirai.numeron.handler.PluginRegister;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
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
        NumeronBot.INSTANCE.turnOff();
    }

    @Command(name = "开关机", dec = "开机", help = "/launch")
    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        bot.getEventChannel().subscribeAlways(MessageEvent.class, event -> {
            if (event.getMessage().contentToString().equals("/launch") && NumeronBot.INSTANCE.getMaster() == event.getSender().getId()) {
                NumeronBot.INSTANCE.turnOn();
                event.getSubject().sendMessage("已开机");
            }
        });
    }
}
