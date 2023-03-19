package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.core.handler.Plugin;
import com.erzbir.numeron.core.handler.PluginRegister;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:07
 * 违禁词检测
 */
@Plugin
@Menu(name = "违禁词检测")
@SuppressWarnings("unused")
public class ScanIllegal implements PluginRegister {

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.filter(f -> f instanceof GroupMessageEvent event
                        && IllegalList.INSTANCE.contains(event.getMessage().contentToString())
                        && event.getGroup().getBotPermission().getLevel() != 0)
                .subscribeAlways(GroupMessageEvent.class, event -> {
                    ((NormalMember) event.getSubject()).mute(30000);
                    event.getSubject()
                            .sendMessage(new PlainText("群: " + event.getGroup().getId() + "\n")
                                    .plus("发送人: " + event.getSender().getId() + "\n")
                                    .plus("消息: " + event.getMessage().contentToString()));
                });
    }
}