package com.erzbir.numeron;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.console.bot.BotLoader;
import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.console.plugin.PluginManager;
import com.erzbir.numeron.core.boot.Starter;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.Bot;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NumeronBotApplication {
    private static final String packageName = "com.erzbir.numeron";
    private static final ExecutorService executorService = Executors.newFixedThreadPool(8);
    private static final String LOGO = """
            | \\ | |_   _ _ __ ___   ___ _ __ ___  _ __ \s
            |  \\| | | | | '_ ` _ \\ / _ \\ '__/ _ \\| '_ \\\s
            | |\\  | |_| | | | | | |  __/ | | (_) | | | |
            |_| \\_|\\__,_|_| |_| |_|\\___|_|  \\___/|_| |_|""";

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        initConfig();
        initPlugin();
        Starter starter = new Starter(packageName, NumeronBotApplication.class.getClassLoader());
        starter.boot(Starter.BotType.PACKAGE);  // 调用 boot 方法初始化
        l = System.currentTimeMillis() - l;
        initLogin();
        printLog(LOGO);
        NumeronLogUtil.info("欢迎使用 Numeron!!!");
        NumeronLogUtil.info("启动耗时: " + l + "ms");
        BotServiceImpl.INSTANCE.getBotList().forEach(Bot::join);
    }

    private static void initPlugin() {
        PluginManager pluginManager = PluginManager.INSTANCE;
        List<Plugin> plugins = pluginManager.getPlugins();
        plugins.forEach(plugin -> {
            NumeronLogUtil.info("插件: " + plugin.getDescription().getName());
            PluginManager.INSTANCE.enable(plugin);
        });
    }

    private static void initLogin() {
        BotServiceImpl.INSTANCE.getBotList().forEach(t -> BotServiceImpl.INSTANCE.login(t));
    }

    private static void initConfig() {
        BotLoader.load();
    }

    private static void printLog(String logo) {
        NumeronLogUtil.info(logo.indent(1));
    }
}

/*
 | \ | |_   _ _ __ ___   ___ _ __ ___  _ __
 |  \| | | | | '_ ` _ \ / _ \ '__/ _ \| '_ \
 | |\  | |_| | | | | | |  __/ | | (_) | | | |
 |_| \_|\__,_|_| |_| |_|\___|_|  \___/|_| |_|
 */
