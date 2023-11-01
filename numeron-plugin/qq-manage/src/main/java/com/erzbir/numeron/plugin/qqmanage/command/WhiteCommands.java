package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.PermissionManager;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.enums.MatchType;
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
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^/permit\\s+?user\\s+?@*\\d+", matchType = MatchType.REGEX_MATCHES)
    private void permit2(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/permit\\s+?user\\s+?@*", ""));
        PermissionManager.INSTANCE.permit(ContactType.USER, id);
        event.getSubject().sendMessage(id + " 已添加到白名单");
    }

    @Command(
            name = "白名单操作",
            dec = "移出白名单",
            help = "/unpermit user [@user] 或者 /unpermit user [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^/unpermit\\s+?user\\s+?@*\\d+", matchType = MatchType.REGEX_MATCHES)
    private void noPermit(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/unpermit\\s+?user\\s+?@*", ""));
        PermissionManager.INSTANCE.down(ContactType.USER, id);
        event.getSubject().sendMessage(id + " 已移出白名单");
    }

    @Command(
            name = "白名单操作",
            dec = "查询白名单",
            help = "/query white [id]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^/query\\s+?white\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void query(MessageEvent event) {
        long l = Long.parseLong(event.getMessage().contentToString().replaceFirst("/query\\s+?white\\s+?", ""));
    }
}
