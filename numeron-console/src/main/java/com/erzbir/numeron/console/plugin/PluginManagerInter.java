package com.erzbir.numeron.console.plugin;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/4/26 21:33
 */
public interface PluginManagerInter {
    List<Plugin> getPlugins();

    String getPluginLibrariesFolder();

    String getPluginsFolder();

    void load(Plugin plugin);

    void enable(Plugin plugin);

    void disable(Plugin plugin);

    void removePlugin(Plugin plugin);

    String getPluginName(Plugin plugin);

    String getPluginAuthor(Plugin plugin);

    String getPluginDec(Plugin plugin);

    String getPluginVersion(Plugin plugin);

}
