package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.enums.MatchType;
import com.erzbir.numeron.api.listener.DefaultListenerRegister;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.qqmanage.DefaultStore;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.MessageRecallEvent;
import net.mamoe.mirai.message.data.FileMessage;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Erzbir
 * @Date: 2022/11/29 13:26
 */
@Listener
@Menu(name = "群防撤回")
@SuppressWarnings("unused")
public class GroupRecall {
    private Boolean preventRecall = false;

    private void register(MessageEvent event) {

        DefaultListenerRegister.INSTANCE.subscribe(event.getBot().getEventChannel(), MessageRecallEvent.GroupRecall.class, event1 -> {
            Object o = event1.getAuthorId();
            MessageChain messageChain = DefaultStore.getInstance().find(o.hashCode());
            if (messageChain != null) {
                if (!messageChain.contains(FileMessage.Key)) {
                    event1.getGroup().sendMessage(new PlainText(event1.getAuthor().getId() + "撤回了一条消息: ").plus("\n\n").plus(messageChain));
                    DefaultStore.getInstance().remove(o.hashCode());
                }
            }
            DefaultListenerRegister.INSTANCE.subscribe(event.getBot().getEventChannel(), GroupMessageEvent.class, event2 -> {
                if (preventRecall) {
                    Object o1 = event2.getSender().getId();
                    DefaultStore.getInstance().save(o1.hashCode(), event2.getMessage(), 2);
                    return ListeningStatus.LISTENING;
                } else {
                    return ListeningStatus.STOPPED;
                }
            });
            return preventRecall ? ListeningStatus.LISTENING : ListeningStatus.STOPPED;
        });
    }

    @Command(
            name = "防撤回",
            dec = "开关启防撤回",
            help = "/prevent_recall [true|false]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "/prevent_recall\\s+?(true|false)", matchType = MatchType.REGEX_MATCHES)
    private void cantRecall(MessageEvent e) {
        preventRecall = Boolean.valueOf(e.getMessage().contentToString().replaceFirst("/prevent_recall\\s+", ""));
        System.out.println(preventRecall);
        e.getSubject().sendMessage("防撤回功能 " + preventRecall);
        if (preventRecall) {
            register(e);
        }
    }
}
