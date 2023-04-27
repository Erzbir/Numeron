package com.erzbir.numeron.plugin.plugincontrol;

import com.erzbir.numeron.api.plugin.PluginService;
import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.handler.Message;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:45
 */
@Menu(name = "插件控制")
@Listener
@SuppressWarnings("unused")
public class Controller {
    @Command(
            name = "查看已加载插件",
            dec = "/查看已加载插件",
            help = "/plugin list -l",
            permission = PermissionType.ALL
    )
    @Message(
            text = "/plugin list -l",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void listLoaded(MessageEvent event) {
        event.getSubject().sendMessage(PluginService.INSTANCE.getLoadedPlugins().toString());
    }

    @Command(
            name = "查看启用的插件",
            dec = "/查看启用的插件",
            help = "plugin list -e",
            permission = PermissionType.ALL
    )
    @Message(
            text = "/plugin list -e",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void listEnable(MessageEvent event) {
        event.getSubject().sendMessage(PluginService.INSTANCE.getEnablePlugins().toString());
    }

    @Command(
            name = "查看未启用的插件",
            dec = "查看未启用的插件",
            help = "/plugin list -d",
            permission = PermissionType.ALL
    )
    @Message(
            text = "/plugin list -d",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void listDisable(MessageEvent event) {
        event.getSubject().sendMessage(PluginService.INSTANCE.getDisablePlugins().toString());
    }

}
