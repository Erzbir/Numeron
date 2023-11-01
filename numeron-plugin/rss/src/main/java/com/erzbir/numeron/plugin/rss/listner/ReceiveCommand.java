package com.erzbir.numeron.plugin.rss.listner;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.api.filter.enums.MatchType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.rss.api.EditApi;
import com.erzbir.numeron.plugin.rss.api.ViewApi;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 13:46
 */
@Listener
@Menu(name = "RSS订阅")
@SuppressWarnings("unused")
public class ReceiveCommand {
    @Command(
            name = "RSS订阅命令",
            dec = "查看指定订阅启用的群和用户",
            help = "#contacts [id]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#contacts\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void echo(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("#contacts\\s+?", "");
        event.getSubject().sendMessage(ViewApi.viewReceiveList(id));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "查看所有订阅启用的群和用户",
            help = "#contacts all",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter("#contact all")
    private void echoAll(MessageEvent event) {
        event.getSubject().sendMessage(ViewApi.viewAllReceiveList());
    }

    @Command(
            name = "RSS订阅命令",
            dec = "添加群订阅",
            help = "#add group [id] [groupId]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#add\\s+?group\\s+?\\d+?\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void addG(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#add\\s+?group\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.addGroup(s[0], Long.parseLong(s[1]));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "取消群订阅",
            help = "#delete group [id] [groupId]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#delete\\s+?group\\s+?\\d+?\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void deleteG(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#delete\\s+?group\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.deleteGroup(s[0], Long.parseLong(s[1]));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "添加用户订阅",
            help = "#add user [id] [groupId]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#add\\s+?user\\s+?\\d+?\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void addU(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#add\\s+?user\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.addUser(s[0], Long.parseLong(s[1]));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "取消用户订阅",
            help = "#delete user [id] [groupId]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#delete\\s+?user\\s+?\\d+?\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void deleteU(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#delete\\s+?group\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.deleteUser(s[0], Long.parseLong(s[1]));
    }
}
