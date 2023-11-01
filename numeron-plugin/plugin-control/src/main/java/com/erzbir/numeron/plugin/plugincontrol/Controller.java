package com.erzbir.numeron.plugin.plugincontrol;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
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
            help = "/plugin list -l"
    )
    @Handler
    @Filter("/plugin list -l")
    private void listLoaded(MessageEvent event) {
        event.getSubject().sendMessage(PluginManager.INSTANCE.getLoadedPlugins().toString());
    }

    @Command(
            name = "查看启用的插件",
            dec = "/查看启用的插件",
            help = "plugin list -e"
    )
    @Handler
    @Filter("/plugin list -e")
    private void listEnable(MessageEvent event) {
        event.getSubject().sendMessage(PluginManager.INSTANCE.getEnablePlugins().toString());
    }

    @Command(
            name = "查看未启用的插件",
            dec = "查看未启用的插件",
            help = "/plugin list -d"
    )
    @Handler
    @Filter("/plugin list -d")
    private void listDisable(MessageEvent event) {
        event.getSubject().sendMessage(PluginManager.INSTANCE.getDisablePlugins().toString());
    }

    //    @Handler
    @Filter(value = "/plugin\\s+\\d+")
    private void removePlugin(MessageEvent event) {
        PluginManager.INSTANCE.removePlugin(PluginManager.INSTANCE.getPlugin(0));
    }

}
