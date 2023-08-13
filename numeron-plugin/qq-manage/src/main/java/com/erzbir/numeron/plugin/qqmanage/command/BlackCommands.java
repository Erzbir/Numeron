package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.entity.BlackServiceImpl;
import com.erzbir.numeron.api.entity.WhiteServiceImpl;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
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
    @MessageFilter(
            text = "^/ban\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void ban(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/ban\\s+?user\\s+?@*", ""));
        WhiteServiceImpl.INSTANCE.removeWhite(id);
        if (BlackServiceImpl.INSTANCE.addBlack(id, event.getSender().getId())) {
            event.getSubject().sendMessage(id + " 添加到黑名单");
        }
    }

    @Command(
            name = "黑名单操作",
            dec = "移出黑名单",
            help = "/noban user [@user] 或者 /noban user [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @MessageFilter(
            text = "^/noban\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void remove(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/noban\\s+?user\\s+?@*", ""));
        if (BlackServiceImpl.INSTANCE.removeBlack(id)) {
            event.getSubject().sendMessage(id + " 已移出黑名单");
        }
    }

    @Command(
            name = "黑名单操作",
            dec = "查询黑名单",
            help = "/query black [qq]",
            permission = PermissionType.MASTER
    )
    @Handler
    @MessageFilter(
            text = "^/query\\s+?black\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void query(MessageEvent event) {
        long l = Long.parseLong(event.getMessage()
                .contentToString()
                .replaceFirst("^/query\\s+?black\\s+?", ""));
        if (l == 0) {
            event.getSubject().sendMessage(BlackServiceImpl.INSTANCE.getBlacks().toString());
        } else {
            event.getSubject().sendMessage(String.valueOf(BlackServiceImpl.INSTANCE.exist(l)));
        }
    }
}
