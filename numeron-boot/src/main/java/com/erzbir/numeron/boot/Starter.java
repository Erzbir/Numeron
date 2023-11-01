package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.console.bot.BotLoader;
import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.console.plugin.PluginManager;
import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public interface Starter {
    void boot();

    @Getter
    class Initializer {
        private final ExecutorService executor = Executors.newFixedThreadPool(6);

        void initPlugin() {
            PluginManager pluginManager = PluginManager.INSTANCE;
            List<Plugin> plugins = pluginManager.getPlugins();
            plugins.forEach(plugin -> executor.submit(() -> {
                NumeronLogUtil.info("插件: " + plugin.getDescription().getName());
                PluginManager.INSTANCE.enable(plugin);
            }));
        }

        void initLogin() {
            executor.submit(() -> BotServiceImpl.INSTANCE.getBotList().forEach(t -> BotServiceImpl.INSTANCE.login(t)));
            executor.shutdown();
        }

        void initConfig() {
            BotLoader.load();
        }
    }
}
