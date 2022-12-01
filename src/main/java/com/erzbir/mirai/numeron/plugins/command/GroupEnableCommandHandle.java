package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.controller.GroupListAction;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:54
 */
@Listener
@SuppressWarnings("unused")
public class GroupEnableCommandHandle {

    @Message(text = "/enable group\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void enable(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        GroupListAction.getInstance().add(id, null, event.getSender().getId());
        event.getSubject().sendMessage("群: " + id + " 已授权");
        System.out.println(GlobalConfig.groupList);
    }

    @Message(text = "/disable group\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void disable(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        GroupListAction.getInstance().remove(id);
        event.getSubject().sendMessage("群: " + id + " 已取消授权");
        System.out.println(GlobalConfig.groupList);
    }

    @Message(text = "/query group\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void query(MessageEvent event) {
        event.getSubject().
                sendMessage(GroupListAction.getInstance()
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
