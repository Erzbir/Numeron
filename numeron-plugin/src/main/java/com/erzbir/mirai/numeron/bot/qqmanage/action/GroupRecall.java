package com.erzbir.mirai.numeron.bot.qqmanage.action;

import com.erzbir.mirai.numeron.bot.common.store.DefaultStore;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.handler.Plugin;
import com.erzbir.mirai.numeron.handler.PluginRegister;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
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
@Listener
@SuppressWarnings("unused")
public class GroupRecall implements PluginRegister {
    private Boolean preventRecall = false;

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> {
            if (preventRecall) {
                MessageChain messageChain = DefaultStore.getInstance().find(event.getMessageIds()[0]);
                assert messageChain != null;
                if (!messageChain.contains(FileMessage.Key)) {
                    event.getGroup().sendMessage(new PlainText(event.getAuthor().getId() + "撤回了一条消息: ").plus("\n\n").plus(messageChain));
                }
            }
        });
    }

    @Command(name = "防撤回", dec = "开关启防撤回", help = "/prevent_recall [true|false]")
    @Message(text = "/prevent_recall\\s+?(true|false)", permission = PermissionType.MASTER, messageRule = MessageRule.REGEX)
    private void cantRecall(MessageEvent e) {
        preventRecall = Boolean.valueOf(e.getMessage().contentToString().split("\\s+")[1]);
        e.getSubject().sendMessage("防撤回功能 " + preventRecall);
    }
}
