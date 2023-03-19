package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.handler.Plugin;
import com.erzbir.numeron.core.handler.PluginRegister;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.core.listener.massage.Message;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.qqmanage.DefaultStore;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
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
@Plugin
@Menu(name = "群防撤回")
@Listener
@SuppressWarnings("unused")
public class GroupRecall implements PluginRegister {
    private Boolean preventRecall = false;

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> {
            if (preventRecall) {
                Object o = event.getAuthorId();
                MessageChain messageChain = DefaultStore.getInstance().find(o.hashCode());
                if (messageChain == null) {
                    return;
                }
                if (!messageChain.contains(FileMessage.Key)) {
                    event.getGroup().sendMessage(new PlainText(event.getAuthor().getId() + "撤回了一条消息: ").plus("\n\n").plus(messageChain));
                }
            }
        });
        channel.subscribeAlways(GroupMessageEvent.class, event -> {
            if (preventRecall) {
                Object o = event.getSender().getId();
                DefaultStore.getInstance().save(o.hashCode(), event.getMessage(), 2);
            }
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
        preventRecall = Boolean.valueOf(e.getMessage().contentToString().split("\\s+")[1]);
        e.getSubject().sendMessage("防撤回功能 " + preventRecall);
    }
}
