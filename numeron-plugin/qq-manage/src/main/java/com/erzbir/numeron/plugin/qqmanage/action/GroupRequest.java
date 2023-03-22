package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.handler.Event;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * @author Erzbir
 * @Date: 2022/12/2 17:44
 */
@Listener
@Menu(name = "入群报告")
@SuppressWarnings("unused")
public class GroupRequest {

    @Event
    private void report(MemberJoinRequestEvent event) {
        Group group = event.getGroup();
        if (group != null) {
            MessageChain messages = new MessageChainBuilder().build();
            messages.plus("有人来了").plus("\n")
                    .plus("ID: ").plus(event.getFromId() + "\n")
                    .plus("昵称: ").plus(event.getFromNick()).plus("\n")
                    .plus("邀请人: ").plus(event.getInvitorId() + "\n")
                    .plus("是否同意?");
            group.sendMessage(messages);
            NumeronBot.INSTANCE.getBot().getEventChannel().filter(f -> f instanceof GroupMessageEvent e && e.getGroup().getId() == group.getId())
                    .subscribeOnce(GroupMessageEvent.class, event1 -> {
                        String s = event1.getMessage().contentToString();
                        if (event1.getSender().getPermission().getLevel() != 0) {
                            if (s.equals("同意")) {
                                event.accept();
                            } else if (s.equals("拒绝")) {
                                event.reject();
                            }
                        }
                    });
        }
    }
}
