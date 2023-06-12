package com.erzbir.numeron.console.plugin;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:36
 */
interface PluginService {
    List<?> getLoadedPlugins();

    List<?> getEnablePlugins();

    List<?> getDisablePlugins();
}