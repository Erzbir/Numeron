package com.erzbir.mirai.numeron.plugins.onOff;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.entity.WhiteList;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 09:26
 * <p>
 * 开关机命令
 * </p>
 */
@Listener
public class OnOffCommandHandle {

    @Command(
            name = "开关机",
            dec = "关机",
            help = "/shutdown",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "/shutdown",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.EQUAL,
            permission = PermissionType.WHITE
    )
    private void shutdown(MessageEvent e) {
        if (NumeronBot.INSTANCE.isEnable()) {
            e.getSubject().sendMessage("关机");
            NumeronBot.INSTANCE.turnOff();
        }
        if (!NumeronBot.INSTANCE.isEnable()) {
            NumeronBot.INSTANCE.getBot().getEventChannel().subscribe(MessageEvent.class, event -> {
                if ((WhiteList.INSTANCE.contains(event.getSender().getId()) || event.getSender().getId() == NumeronBot.INSTANCE.getMaster()) && event.getMessage().contentToString().equals("/launch")) {
                    e.getSubject().sendMessage("开机");
                    NumeronBot.INSTANCE.turnOn();
                    return ListeningStatus.STOPPED;
                } else {
                    return ListeningStatus.LISTENING;
                }
            });
        }
    }

    @Command(name = "开关机",
            dec = "开机",
            help = "/launch",
            permission = PermissionType.MASTER
    )
    public void launch() {
    }
}
