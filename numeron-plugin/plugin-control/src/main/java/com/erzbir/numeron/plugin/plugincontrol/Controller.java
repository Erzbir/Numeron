package com.erzbir.numeron.plugin.plugincontrol;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
import com.erzbir.numeron.console.plugin.PluginManager;
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
    @Handler
    @MessageFilter(
            text = "/plugin list -l",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void listLoaded(MessageEvent event) {
        event.getSubject().sendMessage(PluginManager.INSTANCE.getLoadedPlugins().toString());
    }

    @Command(
            name = "查看启用的插件",
            dec = "/查看启用的插件",
            help = "plugin list -e",
            permission = PermissionType.ALL
    )
    @Handler
    @MessageFilter(
            text = "/plugin list -e",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void listEnable(MessageEvent event) {
        event.getSubject().sendMessage(PluginManager.INSTANCE.getEnablePlugins().toString());
    }

    @Command(
            name = "查看未启用的插件",
            dec = "查看未启用的插件",
            help = "/plugin list -d",
            permission = PermissionType.ALL
    )
    @Handler
    @MessageFilter(
            text = "/plugin list -d",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void listDisable(MessageEvent event) {
        event.getSubject().sendMessage(PluginManager.INSTANCE.getDisablePlugins().toString());
    }

    @Handler
    @MessageFilter(
            text = "/plugin 1",
            messageRule = MessageRule.EQUAL,
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void removePlugin(MessageEvent event) {
        event.getSubject().sendMessage("asdsadasdasdasd");
        PluginManager.INSTANCE.removePlugin(PluginManager.INSTANCE.getPlugin(0));
    }

}
