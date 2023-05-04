package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.listener.EventListenerRegister;
import com.erzbir.numeron.core.bot.NumeronBot;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:07
 * 违禁词检测
 */
@Menu(name = "违禁词检测")
@Listener
@SuppressWarnings("unused")
public class ScanIllegal {
    private boolean flag = false;

    private void register() {
        EventListenerRegister.Bot.register(NumeronBot.INSTANCE.getEventChannel().filter(f -> f instanceof GroupMessageEvent event
                        && IllegalService.INSTANCE.exist(event.getMessage().contentToString())
                        && event.getGroup().getBotPermission().getLevel() != 0),
                GroupMessageEvent.class,
                event -> {
                    ((NormalMember) event.getSubject()).mute(30000);
                    Friend friend = event.getBot().getFriend(NumeronBot.INSTANCE.getMaster());
                    if (friend != null) {
                        friend.sendMessage(new PlainText("群: " + event.getGroup().getId() + "\n")
                                .plus("发送人: " + event.getSender().getId() + "\n")
                                .plus("消息: " + event.getMessage().contentToString()));
                    }
                    return flag ? ListeningStatus.LISTENING : ListeningStatus.STOPPED;
                });
    }

    @Command(
            name = "违禁词扫瞄",
            dec = "开关违禁词扫瞄",
            help = "/scan illegal [true|false]",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^/scan\\s+?illegal\\s+?(true|false)",
            permission = PermissionType.ADMIN,
            messageRule = MessageRule.REGEX
    )
    public void onEvent(MessageEvent event) {
        flag = Boolean.parseBoolean(event.getMessage().contentToString()
                .replaceFirst("^/scan\\s+?illegal\\s+?", ""));
        event.getSubject().sendMessage("违禁词扫瞄 " + flag);
        if (flag) {
            register();
        }
    }
}