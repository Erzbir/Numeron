package com.erzbir.numeron.boot;

import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.console.plugin.PluginManager;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
public class PluginInitializer implements Initializer {
    private PluginManager pluginManager = PluginManager.INSTANCE;

    @Override
    public void init() {
        List<Plugin> plugins = pluginManager.getPlugins();
        plugins.forEach(plugin -> CompletableFuture.runAsync(() -> {
            NumeronLogUtil.info("插件: " + plugin.getDescription().getName());
            PluginManager.INSTANCE.enable(plugin);
        }));
    }
}
