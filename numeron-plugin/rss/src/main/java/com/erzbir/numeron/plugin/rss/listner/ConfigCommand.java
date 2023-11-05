package com.erzbir.numeron.plugin.rss.listner;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.enums.MatchType;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.rss.api.EditApi;
import com.erzbir.numeron.plugin.rss.api.ViewApi;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Erzbir
 * @Date: 2023/3/10 10:48
 */
@Listener
@Menu(name = "RSS订阅")
@Lazy
@SuppressWarnings("unused")
public class ConfigCommand {
    @Command(
            name = "RSS订阅命令",
            dec = "修改订阅名",
            help = "#rename [id] [name]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#rename\\s+?\\d+?\\s+?.+", matchType = MatchType.REGEX_MATCHES)
    private void rename(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("^#rename\\s+?", "").split("\\s+?");
        EditApi.editName(s[0], s[1]);
    }

    @Command(
            name = "RSS订阅命令",
            dec = "修改订阅链接",
            help = "#url [id] [https://xxx.xxx]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#url\\s+?\\d+?\\s+?(http[s]*://.+)", matchType = MatchType.REGEX_MATCHES)
    private void renameUrl(MessageEvent event) {
        String[] s = event.getMessage().contentToString().replaceFirst("^#url\\s+?", "").split("\\s+?");
        EditApi.editUrl(s[0], s[1]);
    }

    @Command(
            name = "RSS订阅命令",
            dec = "查看指定订阅配置",
            help = "#list [id]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#list\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void list(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("#list\\s+?", "");
        event.getSubject().sendMessage(ViewApi.viewRss(id));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "查看所有订阅配置列表",
            help = "#list all",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter("#list all")
    private void listAll(MessageEvent event) {
        String[] split = ViewApi.viewAllRss().split("\\n\\n");
        ForwardMessageBuilder forwardMessageBuilder = new ForwardMessageBuilder(event.getSubject());
        for (String s : split) {
            forwardMessageBuilder.add(event.getBot(), new PlainText(s));
        }
        event.getSubject().sendMessage(forwardMessageBuilder.build());
    }

    @Command(
            name = "RSS订阅命令",
            dec = "查看当前配置",
            help = "#config rss",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter("#config rss")
    private void echoConfig(MessageEvent event) {
        event.getSubject().sendMessage(ViewApi.viewAllConfig());
    }

    @Command(
            name = "RSS订阅命令",
            dec = "保存当前配置",
            help = "#config save",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter("config save")
    private void saveConfig(MessageEvent event) {
        event.getSubject().sendMessage(String.valueOf(EditApi.saveConfig()));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "修改扫瞄延迟",
            help = "#delay [min]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#delay\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void setDelay(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^#delay\\s+?", "");
        EditApi.editDelay(Integer.parseInt(s));
    }

    @Command(
            name = "RSS订阅命令",
            dec = "修改重试次数",
            help = "#retry [times]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#retry\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void setRetryTimes(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^#retry\\s+?", "");
        EditApi.editRetryTimes(Integer.parseInt(s));
    }
}
