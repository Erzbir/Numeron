package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.controller.BlackListAction;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:49
 */
@Listener
@SuppressWarnings("unused")
public class BlackCommandHandle {
    @Message(text = "/ban user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void ban(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        GlobalConfig.whiteList.remove(id);
        BlackListAction.getInstance().add(id, null, event.getSender().getId());
        event.getSubject().sendMessage(id + " 添加到黑名单");
    }

    @Message(text = "/noban user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void remove(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        BlackListAction.getInstance().remove(id);
        event.getSubject().sendMessage(id + " 已移出黑名单");
    }

    @Message(text = "/query black\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void query(MessageEvent event) {
        event.getSubject().
                sendMessage(BlackListAction.getInstance()
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
