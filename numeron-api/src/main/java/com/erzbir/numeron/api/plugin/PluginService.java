package com.erzbir.numeron.api.plugin;

import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:36
 */
public interface PluginService {
    PluginService INSTANCE = ServiceLoader.load(PluginService.class).findFirst().get();

    List<?> getLoadedPlugins();

    List<?> getEnablePlugins();

    List<?> getDisablePlugins();
}
