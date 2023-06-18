package com.erzbir.numeron.console.plugin;

/**
 * @author Erzbir
 * @Date: 2023/6/16 09:52
 */
public interface PluginLoad {
    void load(Plugin plugin);

    void unLoad(Plugin plugin);

    boolean isLoaded(Plugin plugin);
}
