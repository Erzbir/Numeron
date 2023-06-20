package com.erzbir.numeron.console.plugin;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:36
 */
interface PluginService {

    void enable(Plugin plugin);

    void disable(Plugin plugin);

    boolean isLoaded(Plugin plugin);

    void loadPlugin(Plugin plugin);

    void removePlugin(Plugin plugin);

    String getPluginName(Plugin plugin);

    String getPluginAuthor(Plugin plugin);

    String getPluginDec(Plugin plugin);

    String getPluginVersion(Plugin plugin);
}