package com.erzbir.mirai.numeron.configs.controller;

import com.erzbir.mirai.numeron.configs.entity.GroupList;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:54
 * <p>
 * 群列表相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class GroupEnableCommandHandle {

    @Message(text = "/enable group\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void enable(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2]);
        GroupList.INSTANCE.add(id, event.getSender().getId());
        event.getSubject().sendMessage("群: " + id + " 已授权");
    }

    @Message(text = "/disable group\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void disable(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2]);
        GroupList.INSTANCE.remove(id);
        event.getSubject().sendMessage("群: " + id + " 已取消授权");
    }

    @Message(text = "/query group\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(GroupList.INSTANCE
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
