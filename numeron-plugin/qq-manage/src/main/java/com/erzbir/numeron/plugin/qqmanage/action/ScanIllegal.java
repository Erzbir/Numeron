package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.MessageFilter;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.listener.DefaultListenerRegister;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
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

    private void register(MessageEvent event) {
        DefaultListenerRegister.INSTANCE.subscribe(event.getBot().getEventChannel().filter(f -> f instanceof GroupMessageEvent event1
                        && IllegalService.INSTANCE.exist(event.getMessage().contentToString())
                        && event1.getGroup().getBotPermission().getLevel() != 0),
                GroupMessageEvent.class,
                event1 -> {
                    ((NormalMember) event1.getSubject()).mute(30000);
                    Friend friend = event1.getBot().getFriend(BotServiceImpl.INSTANCE.getConfiguration(event.getBot().getId()).getMaster());
                    if (friend != null) {
                        friend.sendMessage(new PlainText("群: " + event1.getGroup().getId() + "\n")
                                .plus("发送人: " + event1.getSender().getId() + "\n")
                                .plus("消息: " + event1.getMessage().contentToString()));
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
    @Handler
    @MessageFilter(
            text = "^/scan\\s+?illegal\\s+?(true|false)",
            permission = PermissionType.ADMIN,
            messageRule = MessageRule.REGEX
    )
    public void onEvent(MessageEvent event) {
        flag = Boolean.parseBoolean(event.getMessage().contentToString()
                .replaceFirst("^/scan\\s+?illegal\\s+?", ""));
        event.getSubject().sendMessage("违禁词扫瞄 " + flag);
        if (flag) {
            register(event);
        }
    }
}