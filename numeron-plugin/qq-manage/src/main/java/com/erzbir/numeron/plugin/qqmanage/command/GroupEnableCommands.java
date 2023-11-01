package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.PermissionManager;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MatchType;
import com.erzbir.numeron.enums.MessageRule;
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
public class GroupEnableCommands {

    @Command(
            name = "群授权操作",
            dec = "授权一个群",
            help = "/enable group [id]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "^/enable\\s+?group\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void enable(MessageEvent event) {
        System.out.println("1212");
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/enable\\s+?group\\s+?", ""));
        PermissionManager.INSTANCE.permit(ContactType.GROUP, id);
        event.getSubject().sendMessage("群: " + id + " 已授权");
    }

    @Command(
            name = "群授权操作",
            dec = "取消授权一个群",
            help = "/disable group [id]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "^/disable\\s+?group\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void disable(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("/disable\\s+?group\\s+?", ""));
        PermissionManager.INSTANCE.removePermission(ContactType.GROUP, id);
        event.getSubject().sendMessage("群: " + id + " 已取消授权");
    }

    @Command(
            name = "群授权操作",
            dec = "查询授权",
            help = "/query group [id]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "^/query\\s+?group\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void query(MessageEvent event) {
        long l = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/query\\s+?group\\s+?", ""));
    }
}
