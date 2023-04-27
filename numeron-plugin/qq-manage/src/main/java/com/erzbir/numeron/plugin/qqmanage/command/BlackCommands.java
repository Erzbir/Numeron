package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.api.model.BlackService;
import com.erzbir.numeron.api.model.WhiteService;
import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.handler.Message;
import com.erzbir.numeron.core.listener.Listener;
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
    @Message(
            text = "^/ban\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void ban(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/ban\\s+?user\\s+?@*", ""));
        WhiteService.INSTANCE.removeWhite(id);
        if (BlackService.INSTANCE.addBlack(id, event.getSender().getId())) {
            event.getSubject().sendMessage(id + " 添加到黑名单");
        }
    }

    @Command(
            name = "黑名单操作",
            dec = "移出黑名单",
            help = "/noban user [@user] 或者 /noban user [qq]",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "^/noban\\s+?user\\s+?@*\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.MASTER
    )
    private void remove(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().replaceFirst("^/noban\\s+?user\\s+?@*", ""));
        if (BlackService.INSTANCE.removeBlack(id)) {
            event.getSubject().sendMessage(id + " 已移出黑名单");
        }
    }

    @Command(
            name = "黑名单操作",
            dec = "查询黑名单",
            help = "/query black [qq]",
            permission = PermissionType.MASTER
    )
    @Message(
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
            event.getSubject().sendMessage(BlackService.INSTANCE.getBlacks().toString());
        } else {
            event.getSubject().sendMessage(String.valueOf(BlackService.INSTANCE.exist(l)));
        }
    }
}
