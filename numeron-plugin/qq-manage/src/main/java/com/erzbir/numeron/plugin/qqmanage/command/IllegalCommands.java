package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.enums.MatchType;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.plugin.qqmanage.action.IllegalService;
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
public class IllegalCommands {

    @Command(
            name = "违禁词操作",
            dec = "添加违禁词",
            help = "/add illegal [key]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "^/add\\s+?illegal\\s+?.+", matchType = MatchType.REGEX_MATCHES)
    private void add(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/add\\s+?illegal\\s+?", "");
        IllegalService.INSTANCE.add(s, event.getSender().getId());
        event.getSubject().sendMessage("违禁词添加成功");
    }

    @Command(
            name = "违禁词操作",
            dec = "删除违禁词",
            help = "/remove illegal [key]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "^/remove\\s+?illegal\\s+?.+", matchType = MatchType.REGEX_MATCHES)
    private void remove(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/remove\\s+?illegal\\s+?", "");
        IllegalService.INSTANCE.remove(s);
        event.getSubject().sendMessage("违禁词删除成功");
    }

    @Command(
            name = "违禁词操作",
            dec = "查询违禁词",
            help = "/query illegal [key]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "^/query\\s+?illegal\\s+?.+", matchType = MatchType.REGEX_MATCHES)
    private void query(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/query\\s+?illegal\\s+?", "");
        if (s.equals("0")) {
            event.getSubject().sendMessage(IllegalService.INSTANCE.getIllegals().toString());
        } else {
            event.getSubject().sendMessage(String.valueOf(IllegalService.INSTANCE.exist(s)));
        }
    }
}
