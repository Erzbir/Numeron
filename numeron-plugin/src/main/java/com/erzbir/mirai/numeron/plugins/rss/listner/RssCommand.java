package com.erzbir.mirai.numeron.plugins.rss.listner;

import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.config.api.PublishApi;
import com.erzbir.mirai.numeron.plugins.rss.config.api.ViewApi;
import com.erzbir.mirai.numeron.plugins.rss.timer.TimerController;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:23
 */
@Listener
public class RssCommand {
    static {
        TimerController.loadAllScan();
    }

    @Command(name = "添加订阅", dec = "#sub [url]", help = "#sub https://xxx.xxx")
    @Message(
            text = "^#sub\\s+?(http[s]*://.*)",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void sub(MessageEvent event) {
        String url = event.getMessage().contentToString().replaceFirst("#sub\\s+?", "");
        PublishApi.addPublish(url);
    }

    @Command(name = "取消订阅", dec = "#nosub <id>", help = "#nosub 1)")
    @Message(
            text = "^#nosub\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void cancel(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("#nosub\\s+?", "");
        PublishApi.disablePublish(id);
    }

    @Command(name = "删除订阅", dec = "#delsub <id>", help = "#delsub 1)")
    @Message(
            text = "^delsub\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void delete(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("#delsub\\s+?", "");
        PublishApi.deletePublish(id);
    }

    @Command(name = "查看指定订阅配置", dec = "#list [id]", help = "#list 1")
    @Message(
            text = "^#list\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void list(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("#list\\s+?", "");
        event.getSubject().sendMessage(ViewApi.viewRss(id));
    }

    @Command(name = "查看所有订阅配置列表", dec = "#list all", help = "#list all")
    @Message(
            text = "#list all",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void listAll(MessageEvent event) {
        event.getSubject().sendMessage(ViewApi.viewAllRss());
    }

    @Command(name = "查看当前配置", dec = "#config rss", help = "#config rss")
    @Message(
            text = "#config rss",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void echoConfig(MessageEvent event) {
        event.getSubject().sendMessage(ViewApi.viewAllConfig());
    }

    @Command(name = "保存当前配置", dec = "#config save", help = "#config save")
    @Message(
            text = "#config save",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void saveConfig(MessageEvent event) {
        event.getSubject().sendMessage(String.valueOf(RssConfig.getInstance().save()));
    }
}
