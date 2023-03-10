package com.erzbir.mirai.numeron.plugins.rss.listner;

import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.rss.api.EditApi;
import com.erzbir.mirai.numeron.plugins.rss.api.ViewApi;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 13:46
 */
@Listener
public class ReceiveCommand {
    @Command(
            name = "查看指定订阅启用的群和用户",
            dec = "#contacts [id]",
            help = "#contacts 1",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^#contacts\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ADMIN
    )
    private void echo(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("#contacts\\s+?", "");
        event.getSubject().sendMessage(ViewApi.viewReceiveList(id));
    }

    @Command(
            name = "查看所有订阅启用的群和用户",
            dec = "#contacts [id]",
            help = "#contacts 1",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "#contacts all",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ADMIN
    )
    private void echoAll(MessageEvent event) {
        event.getSubject().sendMessage(ViewApi.viewAllReceiveList());
    }

    @Command(
            name = "添加群订阅",
            dec = "#add group [id] [groupId]",
            help = "#add group 1 12344556",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^#add\\s+?group\\s+?\\d+?\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ADMIN
    )
    private void addG(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#add\\s+?group\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.addGroup(s[0], Long.parseLong(s[1]));
    }

    @Command(
            name = "取消群订阅",
            dec = "#delete group [id] [groupId]",
            help = "#delete group 1 12344556",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^#delete\\s+?group\\s+?\\d+?\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ADMIN
    )
    private void deleteG(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#delete\\s+?group\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.deleteGroup(s[0], Long.parseLong(s[1]));
    }

    @Command(
            name = "添加用户订阅",
            dec = "#add user [id] [qqId]",
            help = "#add user 1 12344556",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^#add\\s+?user\\s+?\\d+?\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.WHITE
    )
    private void addU(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#add\\s+?user\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.addUser(s[0], Long.parseLong(s[1]));
    }

    @Command(
            name = "取消用户订阅",
            dec = "#delete user [id] [qqId]",
            help = "#delete user 1 12344556",
            permission = PermissionType.WHITE
    )
    @Message(
            text = "^#delete\\s+?user\\s+?\\d+?\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.WHITE
    )
    private void deleteU(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("#delete\\s+?group\\s+?", "").split("\\s+?");
        EditApi.EditReceiveListApi.deleteUser(s[0], Long.parseLong(s[1]));
    }
}
