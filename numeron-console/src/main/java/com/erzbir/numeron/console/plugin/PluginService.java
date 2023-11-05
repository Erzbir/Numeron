package com.erzbir.numeron.console.plugin;

import java.io.File;
import java.util.List;

/**
 * PluginService
 *
 * @author Erzbir
 * @Date: 2023/4/27 19:36
 */
interface PluginService {

    void enable(Plugin plugin);

    void disable(Plugin plugin);

    void reload(Plugin plugin);

    void load(Plugin plugin);

    boolean isLoaded(Plugin plugin);

    void removePlugin(Plugin plugin);

    String getPluginName(Plugin plugin);

    String getPluginAuthor(Plugin plugin);

    String getPluginDec(Plugin plugin);

    String getPluginVersion(Plugin plugin);

    List<?> getLoadedPlugins();

    List<?> getEnablePlugins();

    List<?> getDisablePlugins();

    List<Plugin> getPlugins();

    Plugin getPlugin(int index);

    Plugin getPlugin(String plugin);

    String getPluginsFolder();

    File[] getUnloadedPlugins();
}