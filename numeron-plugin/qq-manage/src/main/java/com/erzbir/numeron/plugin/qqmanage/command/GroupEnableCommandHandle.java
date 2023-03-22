package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.core.entity.GroupList;
import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.handler.Message;
import com.erzbir.numeron.core.listener.Listener;
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

    @Command(
            name = "群授权操作",
            dec = "授权一个群",
            help = "/enable group [id]",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^/enable\\s+?group\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void enable(MessageEvent event) {
        System.out.println("1212");
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/enable\\s+?group\\s+?", ""));
        GroupList.INSTANCE.add(id, event.getSender().getId());
        event.getSubject().sendMessage("群: " + id + " 已授权");
    }

    @Command(
            name = "群授权操作",
            dec = "取消授权一个群",
            help = "/disable group [id]",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^/disable\\s+?group\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.WHITE
    )
    private void disable(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("/disable\\s+?group\\s+?", ""));
        GroupList.INSTANCE.remove(id);
        event.getSubject().sendMessage("群: " + id + " 已取消授权");
    }

    @Command(
            name = "群授权操作",
            dec = "查询授权",
            help = "/query group [id]",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^/query\\s+?group\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(GroupList.INSTANCE
                        .query(Long.parseLong(event.getMessage().contentToString().replaceFirst("^/query\\s+?group\\s+?\\d+", ""))));
    }
}
