package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.console.exception.PluginConflictException;
import com.erzbir.numeron.console.exception.PluginLoadException;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2023/4/26 17:36
 */
public class PluginManager implements PluginManagerInter, PluginService {
    private static final String pluginPath = "numeron_plugins/";
    public static PluginManager INSTANCE = new PluginManager();

    static {
        ConfigCreateUtil.createDir(pluginPath);
    }

    public final Map<String, ServiceLoader<Plugin>> serviceLoaderMap = new ConcurrentHashMap<>();
    public final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, Plugin> pluginMap = new ConcurrentHashMap<>();

    private PluginManager() {
        loadAll();
    }


    private void loadAll() {
        File file = new File(pluginPath);
        File[] files = file.listFiles(t -> t.getName().endsWith(".jar"));
        if (files != null) {
            for (File file1 : files) {
                loadPlugin(file1);
            }
        }
    }

    private void loadPlugin(File plugin) {
        try {
            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{plugin.getAbsoluteFile().toURI().toURL()});
            ServiceLoader<Plugin> load = ServiceLoader.load(Plugin.class, urlClassLoader);
            load.forEach(t -> {
                String id = t.getClass().getName();
                if (pluginMap.get(id) != null) {
                    throw new PluginConflictException("插件冲突");
                }
                load(t);
                serviceLoaderMap.put(id, load);
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new PluginLoadException("插件加载异常" + e);
        }
    }

    @Override
    public void enable(Plugin plugin) {
        if (plugin.isEnable()) {
            NumeronLogUtil.logger.error("Plugin" + plugin.getDescription().getName() + "is already enabled and cannot be re-enabled.");
            return;
        }
        executor.submit(plugin::enable);
        Class<? extends Plugin> aClass = plugin.getClass();
        AppContext.INSTANCE.addAllToContext(aClass.getPackageName(), aClass.getClassLoader());
    }

    @Override
    public void disable(Plugin plugin) {
        if (!plugin.isEnable()) {
            NumeronLogUtil.logger.error("Plugin" + plugin.getDescription().getName() + "is already disabled and cannot be re-disabled.");
            return;
        }
        executor.submit(plugin::disable);
        AppContext.INSTANCE.removeBean(plugin.getClass());
    }

    @Override
    public void removePlugin(Plugin plugin) {
        pluginMap.remove(plugin.getClass().getName()).onUnLoad();
    }

    @Override
    public List<Plugin> getPlugins() {
        ArrayList<Plugin> list = new ArrayList<>();
        pluginMap.forEach((k, v) -> list.add(v));
        return list;
    }

    @Override
    public String getPluginsFolder() {
        return pluginPath;
    }

    @Override
    public void load(Plugin plugin) {
        plugin.onLoad();
        Class<? extends Plugin> aClass = plugin.getClass();
        pluginMap.put(aClass.getName(), plugin);
    }

    @Override
    public String getPluginName(Plugin plugin) {
        return plugin.getDescription().getName();
    }

    @Override
    public String getPluginAuthor(Plugin plugin) {
        return plugin.getDescription().getAuthor();
    }

    @Override
    public String getPluginDec(Plugin plugin) {
        return plugin.getDescription().getDesc();

    }

    @Override
    public String getPluginVersion(Plugin plugin) {
        return plugin.getDescription().getVersion();
    }

    @Override
    public List<Plugin> getLoadedPlugins() {
        return pluginMap.values().stream().toList();
    }

    @Override
    public List<Plugin> getEnablePlugins() {
        List<Plugin> ret = new ArrayList<>();
        pluginMap.values().forEach(t -> {
            if (t.isEnable()) {
                ret.add(t);
            }
        });
        return ret;
    }

    @Override
    public List<Plugin> getDisablePlugins() {
        List<Plugin> ret = new ArrayList<>();
        pluginMap.values().forEach(t -> {
            if (!t.isEnable()) {
                ret.add(t);
            }
        });
        return ret;
    }
}
