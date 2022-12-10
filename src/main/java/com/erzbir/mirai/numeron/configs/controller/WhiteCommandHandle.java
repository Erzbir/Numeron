package com.erzbir.mirai.numeron.configs.controller;

import com.erzbir.mirai.numeron.configs.entity.WhiteList;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:50
 * <p>
 * 白名单相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class WhiteCommandHandle {

    @Message(text = "/permit user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void permit2(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2].replaceAll("@", ""));
        WhiteList.INSTANCE.remove(id);
        WhiteList.INSTANCE.add(id, event.getSender().getId());
        event.getSubject().sendMessage(id + " 已添加到白名单");
    }

    @Message(text = "/unpermit user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void noPermit(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2]);
        WhiteList.INSTANCE.remove(id);
        event.getSubject().sendMessage(id + " 已移出白名单");
    }

    @Message(text = "/query white\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(WhiteList.INSTANCE
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
