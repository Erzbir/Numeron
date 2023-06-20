package com.erzbir.numeron.console.plugin;

import java.io.File;

/**
 * @author Erzbir
 * @Date: 2023/6/16 09:52
 */
public interface PluginLoad {
    void load(File plugin);

    void reload(Plugin plugin);
}
