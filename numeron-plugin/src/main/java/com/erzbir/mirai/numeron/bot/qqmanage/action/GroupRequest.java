package com.erzbir.mirai.numeron.bot.qqmanage.action;

import com.erzbir.mirai.numeron.entity.BlackList;
import com.erzbir.mirai.numeron.handler.Plugin;
import com.erzbir.mirai.numeron.handler.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * @author Erzbir
 * @Date: 2022/12/2 17:44
 */
@Plugin
public class GroupRequest implements PluginRegister {

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.filter(f -> f instanceof MemberJoinRequestEvent event && !BlackList.INSTANCE.contains(event.getFromId()))
                .subscribeAlways(MemberJoinRequestEvent.class, event -> {
                    Group group = event.getGroup();
                    if (group != null) {
                        MessageChain messages = new MessageChainBuilder().build();
                        messages.plus("有人来了").plus("\n")
                                .plus("ID: ").plus(event.getFromId() + "\n")
                                .plus("昵称: ").plus(event.getFromNick()).plus("\n")
                                .plus("邀请人: ").plus(event.getInvitorId() + "\n")
                                .plus("是否同意?");
                        group.sendMessage(messages);
                        channel.subscribe(GroupMessageEvent.class, event1 -> {
                            String s = event1.getMessage().contentToString();
                            if (event1.getSender().getPermission().getLevel() != 0) {
                                if (s.equals("同意")) {
                                    event.accept();
                                } else if (s.equals("拒绝")) {
                                    event.reject();
                                }
                            }
                            return ListeningStatus.STOPPED;
                        });
                    }
                });
    }
}
