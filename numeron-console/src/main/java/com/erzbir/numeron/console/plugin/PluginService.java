package com.erzbir.numeron.console.plugin;

import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:36
 */
interface PluginService {
    List<?> getLoadedPlugins();

    List<?> getEnablePlugins();

    List<?> getDisablePlugins();
}