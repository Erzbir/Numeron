package com.erzbir.numeron.plugin.rss.listner;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.filter.enums.MatchType;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.rss.api.PublishApi;
import com.erzbir.numeron.plugin.rss.timer.TimerController;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:23
 */
@Listener
@Menu(name = "RSS订阅")
@SuppressWarnings("unused")
public class RssCommand {
    @Command(
            name = "RSS订阅命令",
            dec = "开启推送",
            help = "#rss",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter("#rss")
    private void open(MessageEvent event) {
        TimerController.loadAllScan();
    }

    @Command(
            name = "RSS订阅命令",
            dec = "添加订阅",
            help = "#sub [https://xxx.xxx]"
    )
    @Handler
    @Filter(value = "^#sub\\s+?(http[s]*://.*)", matchType = MatchType.REGEX_MATCHES)
    private void sub(MessageEvent event) {
        String url = event.getMessage().contentToString().replaceFirst("^#sub\\s+?", "");
        PublishApi.addPublish(url);
    }

    @Command(
            name = "RSS订阅命令",
            dec = "取消订阅",
            help = "#nosub [id]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#nosub\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void cancel(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("^#nosub\\s+?", "");
        PublishApi.disablePublish(id);
    }

    @Command(
            name = "RSS订阅命令",
            dec = "开启订阅",
            help = "#ensub [id]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^#ensub\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void enable(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("^#ensub\\s+?", "");
        PublishApi.enablePublish(id);
    }

    @Command(
            name = "RSS订阅命令",
            dec = "删除订阅",
            help = "#delsub [id]",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter(value = "^#delsub\\s+?\\d+", matchType = MatchType.REGEX_MATCHES)
    private void delete(MessageEvent event) {
        String id = event.getMessage().contentToString().replaceFirst("^#delsub\\s+?", "");
        PublishApi.deletePublish(id);
    }

    @Command(
            name = "RSS订阅命令",
            dec = "停止扫瞄",
            help = "#disable scan",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter("#disable scan")
    private void disableAllScan(MessageEvent event) {
        PublishApi.disableScan();
    }

    @Command(
            name = "RSS订阅命令",
            dec = "开启扫瞄",
            help = "#enable scan",
            permission = PermissionType.MASTER
    )
    @Handler
    @Permission(permission = PermissionType.MASTER)
    @Filter("#enable scan")
    private void enableAllScan(MessageEvent event) {
        PublishApi.enableScan();
    }
}
