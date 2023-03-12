package com.erzbir.mirai.numeron.plugins.qqmanage.command;

import com.erzbir.mirai.numeron.entity.WhiteList;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
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

    @Command(
            name = "白名单操作",
            dec = "添加白名单",
            help = "/permit user [@user] 或者 /permit user [qq]",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "^/permit\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void permit2(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/permit\\s+?user\\s+?@*", ""));
        WhiteList.INSTANCE.add(id, event.getSender().getId());
        event.getSubject().sendMessage(id + " 已添加到白名单");
    }

    @Command(
            name = "白名单操作",
            dec = "移出白名单",
            help = "/unpermit user [@user] 或者 /unpermit user [qq]",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "^/unpermit\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void noPermit(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/unpermit\\s+?user\\s+?@*", ""));
        WhiteList.INSTANCE.remove(id);
        event.getSubject().sendMessage(id + " 已移出白名单");
    }

    @Command(
            name = "白名单操作",
            dec = "查询白名单",
            help = "/query white [id]",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "^/query\\s+?white\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(WhiteList.INSTANCE
                        .query(Long.parseLong(event.getMessage().contentToString().replaceFirst("/query\\s+?white\\s+?", ""))));
    }
}
