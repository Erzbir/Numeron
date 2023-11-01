package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.PermissionManager;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.api.filter.enums.MatchType;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:49
 * <p>
 * 黑名单的相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class BlackCommands {

    @Command(
            name = "黑名单操作",
            dec = "拉入黑名单",
            help = "/ban user [@user] 或者 /ban user [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^/ban\\s+?user\\s+?@*\\d+", matchType = MatchType.REGEX_MATCHES)
    private void ban(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/ban\\s+?user\\s+?@*", ""));
        PermissionManager.INSTANCE.ban(ContactType.USER, id);
    }

    @Command(
            name = "黑名单操作",
            dec = "移出黑名单",
            help = "/noban user [@user] 或者 /noban user [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^/noban\\s+?user\\s+?@*\\d+", matchType = MatchType.REGEX_MATCHES)
    private void remove(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/noban\\s+?user\\s+?@*", ""));
        PermissionManager.INSTANCE.permit(ContactType.USER, id);
    }

    @Command(
            name = "黑名单操作",
            dec = "查询黑名单",
            help = "/query black [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^/query\\s+?black\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void query(MessageEvent event) {
        long l = Long.parseLong(event.getMessage()
                .contentToString()
                .replaceFirst("^/query\\s+?black\\s+?", ""));

    }
}
