package com.erzbir.numeron.plugin.qqmanage.action;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.entity.BlackServiceImpl;
import com.erzbir.numeron.api.listener.DefaultListenerRegister;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:07
 * 黑名单检测
 */
@Menu(name = "黑名单检测")
@Listener
@SuppressWarnings("unused")
public class ScanBlack {
    private boolean flag = false;

    private void register(MessageEvent event) {
        DefaultListenerRegister.INSTANCE.subscribe(
                event.getBot().getEventChannel().filter(f -> f instanceof GroupMessageEvent event1
                        && BlackServiceImpl.INSTANCE.exist(event1.getSender().getId())
                        && event1.getGroup().getBotPermission().getLevel() != 0),
                GroupMessageEvent.class, event1 -> {
                    ((NormalMember) event.getSender()).kick("黑名单用户");
                    return flag ? ListeningStatus.LISTENING : ListeningStatus.STOPPED;
                });
    }


    @Command(
            name = "黑名单扫瞄",
            dec = "黑名单扫瞄",
            help = "/scan illegal [true|false]",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^/scan\\s+?black\\s+?(true|false)",
            permission = PermissionType.ADMIN,
            messageRule = MessageRule.REGEX
    )
    public void onEvent(MessageEvent event) {
        flag = Boolean.parseBoolean(event.getMessage().contentToString()
                .replaceFirst("^/scan\\s+?black\\s+?", ""));
        event.getSubject().sendMessage("黑名单扫瞄 " + flag);
        if (flag) {
            register(event);
        }
    }
}