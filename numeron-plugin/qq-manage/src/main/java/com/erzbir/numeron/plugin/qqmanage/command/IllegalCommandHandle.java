package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.core.listener.massage.Message;
import com.erzbir.numeron.plugin.qqmanage.action.IllegalList;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:48
 * <p>
 * 违禁词列表相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class IllegalCommandHandle {

    @Command(
            name = "违禁词操作",
            dec = "添加违禁词",
            help = "/add illegal [key]",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^/add\\s+?illegal\\s+?.+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void add(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/add\\s+?illegal\\s+?", "");
        IllegalList.INSTANCE.add(s, event.getSender().getId());
        event.getSubject().sendMessage("违禁词添加成功");
    }

    @Command(
            name = "违禁词操作",
            dec = "删除违禁词",
            help = "/remove illegal [key]",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^/remove\\s+?illegal\\s+?.+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void remove(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/remove\\s+?illegal\\s+?", "");
        IllegalList.INSTANCE.remove(s);
        event.getSubject().sendMessage("违禁词删除成功");
    }

    @Command(
            name = "违禁词操作",
            dec = "查询违禁词",
            help = "/query illegal [key]",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^/query\\s+?illegal\\s+?.+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(IllegalList.INSTANCE
                        .query(event.getMessage().contentToString().replaceFirst("^/query\\s+?illegal\\s+?", "")));
    }
}
