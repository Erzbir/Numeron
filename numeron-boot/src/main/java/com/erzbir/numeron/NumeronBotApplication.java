package com.erzbir.numeron;

import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.console.plugin.PluginManager;
import com.erzbir.numeron.core.boot.Starter;
import com.erzbir.numeron.core.bot.NumeronBot;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.util.List;

public class NumeronBotApplication {
    private static final String packageName = "com.erzbir.numeron";

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        AppContext instance = AppContext.INSTANCE;
        PluginManager pluginManager = PluginManager.INSTANCE;
        List<Plugin> plugins = pluginManager.getPlugins();
        plugins.forEach(plugin -> {
            NumeronLogUtil.info("插件: " + plugin.getDescription().getName());
            PluginManager.INSTANCE.enable(plugin);
        });
        Starter starter = new Starter(packageName, NumeronBotApplication.class.getClassLoader());
        starter.boot();  // 调用boot方法启动
        l = System.currentTimeMillis() - l;
        NumeronBot.INSTANCE.login();
        NumeronLogUtil.info("""
                | \\ | |_   _ _ __ ___   ___ _ __ ___  _ __ \s
                |  \\| | | | | '_ ` _ \\ / _ \\ '__/ _ \\| '_ \\\s
                | |\\  | |_| | | | | | |  __/ | | (_) | | | |
                |_| \\_|\\__,_|_| |_| |_|\\___|_|  \\___/|_| |_|""".indent(1));
        NumeronLogUtil.info("欢迎使用Numeron!!!");
        NumeronLogUtil.info("启动耗时: " + l + "ms");
    }
}

/*
 | \ | |_   _ _ __ ___   ___ _ __ ___  _ __
 |  \| | | | | '_ ` _ \ / _ \ '__/ _ \| '_ \
 | |\  | |_| | | | | | |  __/ | | (_) | | | |
 |_| \_|\__,_|_| |_| |_|\___|_|  \___/|_| |_|
 */
