package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.listener.EventListenerRegister;
import com.erzbir.numeron.api.listener.ListenerRegister;
import com.erzbir.numeron.core.bot.NumeronBot;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
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

    private void register() {

        ListenerRegister.INStANCE.Bot.subscribe(NumeronBot.INSTANCE.getEventChannel(), MessageRecallEvent.GroupRecall.class, event -> {
            Object o = event.getAuthorId();
            MessageChain messageChain = DefaultStore.getInstance().find(o.hashCode());
            if (messageChain != null) {
                if (!messageChain.contains(FileMessage.Key)) {
                    event.getGroup().sendMessage(new PlainText(event.getAuthor().getId() + "撤回了一条消息: ").plus("\n\n").plus(messageChain));
                    DefaultStore.getInstance().remove(o.hashCode());
                }
            }
            ListenerRegister.INStANCE.Bot.subscribe(NumeronBot.INSTANCE.getEventChannel(), GroupMessageEvent.class, event1 -> {
                if (preventRecall) {
                    Object o1 = event1.getSender().getId();
                    DefaultStore.getInstance().save(o1.hashCode(), event1.getMessage(), 2);
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
    @Message(
            text = "/prevent_recall\\s+?(true|false)",
            permission = PermissionType.ADMIN,
            messageRule = MessageRule.REGEX
    )
    private void cantRecall(MessageEvent e) {
        preventRecall = Boolean.valueOf(e.getMessage().contentToString().replaceFirst("/prevent_recall\\s+", ""));
        System.out.println(preventRecall);
        e.getSubject().sendMessage("防撤回功能 " + preventRecall);
        if (preventRecall) {
            register();
        }
    }
}
