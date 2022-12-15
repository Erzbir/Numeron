package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.configs.BotConfig;
import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.configs.entity.IllegalList;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.PlainText;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:07
 * 违禁词检测
 */
@Listener
@Plugin
@SuppressWarnings("unused")
public class ScanIllegal implements PluginRegister {

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.filter(f -> GlobalConfig.OPEN && f instanceof GroupMessageEvent event
                        && IllegalList.INSTANCE.contains(event.getMessage().contentToString())
                        && event.getGroup().getBotPermission().getLevel() != 0)
                .subscribeAlways(GroupMessageEvent.class, event -> {
                    ((NormalMember) event.getSubject()).mute(30000);
                    Objects.requireNonNull(bot.getFriend(BotConfig.INSTANCE.getMaster()))
                            .sendMessage(new PlainText("群: " + event.getGroup().getId() + "\n")
                                    .plus("发送人: " + event.getSender().getId() + "\n")
                                    .plus("消息: " + event.getMessage().contentToString()));
                });
    }
}