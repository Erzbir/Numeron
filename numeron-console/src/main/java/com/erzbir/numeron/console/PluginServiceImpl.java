package com.erzbir.numeron.console;

import com.erzbir.numeron.api.plugin.PluginService;
import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.console.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/4/27 19:39
 */
public class PluginServiceImpl implements PluginService {
    @Override
    public List<?> getLoadedPlugins() {
        List<Plugin> plugins = PluginManager.INSTANCE.getPlugins();
        List<String> ret = new ArrayList<>();
        plugins.forEach(t -> ret.add(t.getDescription().getName()));
        return ret;
    }

    @Override
    public List<?> getEnablePlugins() {
        List<Plugin> plugins = PluginManager.INSTANCE.getPlugins();
        List<String> ret = new ArrayList<>();
        plugins.forEach(t -> {
            if (t.isEnable()) {
                ret.add(t.getDescription().getName());
            }
        });
        return ret;
    }

    @Override
    public List<?> getDisablePlugins() {
        List<Plugin> plugins = PluginManager.INSTANCE.getPlugins();
        List<String> ret = new ArrayList<>();
        plugins.forEach(t -> {
            if (!t.isEnable()) {
                ret.add(t.getDescription().getName());
            }
        });
        return ret;
    }
}
