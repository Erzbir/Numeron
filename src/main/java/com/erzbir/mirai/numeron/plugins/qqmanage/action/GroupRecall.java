package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import com.erzbir.mirai.numeron.store.DefaultStore;
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
    private boolean preventRecall = false;

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        bot.getEventChannel().subscribeAlways(MessageRecallEvent.GroupRecall.class, event -> {
            if (preventRecall) {
                MessageChain messageChain = DefaultStore.INSTANCE.find(event.getMessageIds()[0]);
                assert messageChain != null;
                if (!messageChain.contains(FileMessage.Key)) {
                    event.getGroup().sendMessage(new PlainText(event.getAuthor().getId() + "撤回了一条消息: ").plus("\n\n").plus(messageChain));
                }
            }
        });
    }

    @Message(text = "/prevent_recall enable", permission = PermissionType.MASTER, filterRule = FilterRule.NONE)
    public void cantRecall(MessageEvent e) {
        preventRecall = true;
        e.getSubject().sendMessage("已开启防撤回功能");
    }

    @Message(text = "/prevent_recall disable", permission = PermissionType.MASTER, filterRule = FilterRule.NONE)
    public void canRecall(MessageEvent e) {
        preventRecall = true;
        e.getSubject().sendMessage("已关闭防撤回功能");
    }
}
