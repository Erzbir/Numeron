package com.erzbir.mirai.numeron.plugins.rss.listner;

import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.rss.api.PublishApi;
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

    @Command(
            name = "添加订阅",
            dec = "#sub [url]",
            help = "#sub https://xxx.xxx",
            permission = PermissionType.ALL
    )
    @Message(
            text = "^#sub\\s+?(http[s]*://.*)",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void sub(MessageEvent event) {
        String url = event.getMessage().contentToString().replaceFirst("^#sub\\s+?", "");
        PublishApi.addPublish(url);
    }

    @Command(
            name = "取消订阅",
            dec = "#nosub <id>",
            help = "#nosub 1)",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^#nosub\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ADMIN
    )
    private void cancel(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("^#nosub\\s+?", "");
        PublishApi.disablePublish(id);
    }

    @Command(
            name = "开启订阅",
            dec = "#ensub <id>",
            help = "#ensub 1)",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "^#ensub\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ADMIN
    )
    private void enable(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("^#ensub\\s+?", "");
        PublishApi.enablePublish(id);
    }

    @Command(
            name = "删除订阅",
            dec = "#delsub <id>",
            help = "#delsub 1",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "^#delsub\\s+?\\d+",
            messageRule = MessageRule.REGEX,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void delete(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("^#delsub\\s+?", "");
        PublishApi.deletePublish(id);
    }

    @Command(
            name = "停止扫瞄",
            dec = "#disable scan",
            help = "#disable scan",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "#disable scan",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void disableAllScan(MessageEvent event) {
        PublishApi.disableScan();
    }

    @Command(
            name = "开启扫瞄",
            dec = "#enable scan",
            help = "#enable scan",
            permission = PermissionType.MASTER
    )
    @Message(
            text = "#enable scan",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void enableAllScan(MessageEvent event) {
        PublishApi.disableScan();
    }
}
