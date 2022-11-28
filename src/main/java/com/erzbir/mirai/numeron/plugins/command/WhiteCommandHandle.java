package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.controller.WhiteListAction;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:50
 */
@Listener
@SuppressWarnings("unused")
public class WhiteCommandHandle {

    @Message(text = "/permit user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void permit(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        WhiteListAction.getInstance().remove(id);
        WhiteListAction.getInstance().add(id, null, event.getSender().getId());
        event.getSubject().sendMessage(id + " 已添加到白名单");
    }

    @Message(text = "/unpermit user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void noPermit(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        WhiteListAction.getInstance().remove(id);
        event.getSubject().sendMessage(id + " 已移出白名单");
    }

    @Message(text = "/query white\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void query(MessageEvent event) {
        event.getSubject().
                sendMessage(WhiteListAction.getInstance()
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
