package com.erzbir.numeron.console.plugin;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/4/26 21:33
 */
interface PluginManagerInter {
    List<?> getLoadedPlugins();

    List<?> getEnablePlugins();

    List<?> getDisablePlugins();

    List<Plugin> getPlugins();

    Plugin getPlugin(int index);

    Plugin getPlugin(String plugin);

    String getPluginsFolder();

}
