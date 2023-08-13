package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.entity.WhiteServiceImpl;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
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
public class WhiteCommands {

    @Command(
            name = "白名单操作",
            dec = "添加白名单",
            help = "/permit user [@user] 或者 /permit user [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @MessageFilter(
            text = "^/permit\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void permit2(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/permit\\s+?user\\s+?@*", ""));
        if (WhiteServiceImpl.INSTANCE.addWhite(id, event.getSender().getId())) {
            event.getSubject().sendMessage(id + " 已添加到白名单");
        }
    }

    @Command(
            name = "白名单操作",
            dec = "移出白名单",
            help = "/unpermit user [@user] 或者 /unpermit user [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @MessageFilter(
            text = "^/unpermit\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void noPermit(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/unpermit\\s+?user\\s+?@*", ""));
        if (WhiteServiceImpl.INSTANCE.removeWhite(id)) {
            event.getSubject().sendMessage(id + " 已移出白名单");
        }
    }

    @Command(
            name = "白名单操作",
            dec = "查询白名单",
            help = "/query white [id]",
            permission = PermissionType.MASTER
    )
    @Handler
    @MessageFilter(
            text = "^/query\\s+?white\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void query(MessageEvent event) {
        long l = Long.parseLong(event.getMessage().contentToString().replaceFirst("/query\\s+?white\\s+?", ""));
        if (l == 0) {
            event.getSubject().sendMessage(WhiteServiceImpl.INSTANCE.getWhites().toString());
        } else {
            event.getSubject().sendMessage(String.valueOf(WhiteServiceImpl.INSTANCE.exist(l)));
        }
    }
}
