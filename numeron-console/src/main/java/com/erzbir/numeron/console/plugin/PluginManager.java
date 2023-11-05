package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.api.context.DefaultAppContext;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.console.plugin.loader.DefaultPluginLoader;
import com.erzbir.numeron.core.context.MiraiListenerContext;
import com.erzbir.numeron.exception.PluginConflictException;
import com.erzbir.numeron.exception.PluginIllegalException;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.File;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erzbir
 * @Date: 2023/4/26 17:36
 */
public class PluginManager implements PluginService {
    private static final String pluginPath = "numeron_plugins/";
    public static PluginManager INSTANCE = new PluginManager();

    static {
        ConfigCreateUtil.createDir(pluginPath);
    }

    private PluginRegister pluginEventRegister = new PluginEventRegister();
    private Map<String, Plugin> pluginMap = new ConcurrentHashMap<>();


    private PluginManager() {
        loadAll();
    }


    private void loadAll() {
        File file = new File(pluginPath);
        File[] files = file.listFiles(t -> t.getName().endsWith(".jar"));
        if (files != null) {
            for (File file1 : files) {
                load(file1);
            }
        }
    }

    public void load(File plugin) {
        try {
            if (!plugin.getName().endsWith(".jar")) {
                throw new PluginIllegalException("not a legal plugin");
            }
            DefaultPluginLoader pluginLoader = new DefaultPluginLoader();
            pluginLoader.load(plugin);
            pluginLoader.loadedPlugins.forEach(p -> {
                Set<Class<?>> collect = new HashSet<>(pluginLoader.loadedClass);
                PluginContext pluginContext = new PluginContext(collect, plugin);
                p.setPluginContext(pluginContext);
                load(p);
                pluginEventRegister.register(pluginContext);
            });
        } catch (Exception e) {
            NumeronLogUtil.logger.error("ERROR", new PluginIllegalException(e));
        }
    }

    @Override
    public void reload(Plugin plugin) {
        if (plugin == null) {
            return;
        }
        pluginMap.remove(plugin.getClass().getName());
        CompletableFuture.runAsync(() -> {
            PluginContext pluginContext = plugin.getPluginContext();
            File file = pluginContext.getFile();
            unload(plugin);
            System.gc();
            load(file);

        }).thenRunAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
            DefaultAppContext.INSTANCE.getProcessors().forEach(Processor::onApplicationEvent);
        });
    }

    @Override
    public void enable(Plugin plugin) {
        if (plugin == null) {
            return;
        }
        if (plugin.isEnable()) {
            NumeronLogUtil.logger.error("Plugin" + plugin.getDescription().getName() + "is already enabled and cannot be re-enabled.");
            return;
        }
        plugin.enable();
    }

    @Override
    public void disable(Plugin plugin) {
        if (plugin == null) {
            return;
        }
        if (!plugin.isEnable()) {
            NumeronLogUtil.logger.error("Plugin" + plugin.getDescription().getName() + "is already disabled and cannot be re-disabled.");
            return;
        }
        plugin.disable();
    }

    public void unload(Plugin plugin) {
        MiraiListenerContext.INSTANCE.cancelAll();
        plugin.disable();
        PluginContext pluginContext = plugin.getPluginContext();
        pluginContext.destroy();
        System.gc();
    }

    @Override
    public void removePlugin(Plugin plugin) {
        if (plugin == null) {
            return;
        }
        pluginMap.remove(plugin.getClass().getName());
        CompletableFuture.runAsync(() -> unload(plugin)).thenRunAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {
            }
            DefaultAppContext.INSTANCE.getProcessors().forEach(Processor::onApplicationEvent);
        });
    }

    @Override
    public List<Plugin> getPlugins() {
        ArrayList<Plugin> list = new ArrayList<>();
        pluginMap.forEach((k, v) -> list.add(v));
        return list;
    }

    @Override
    public Plugin getPlugin(int index) {
        return getPlugins().get(index);
    }

    @Override
    public Plugin getPlugin(String plugin) {
        return pluginMap.get(plugin);
    }

    @Override
    public String getPluginsFolder() {
        return pluginPath;
    }

    @Override
    public File[] getUnloadedPlugins() {
        File file = new File(pluginPath);
        return file.listFiles(t -> t.getName().endsWith(".jar"));
    }

    @Override
    public boolean isLoaded(Plugin plugin) {
        return pluginMap.containsValue(plugin);
    }

    @Override
    public void load(Plugin plugin) {
        if (isLoaded(plugin)) {
            NumeronLogUtil.logger.error("ERROR", new PluginConflictException("Plugin " + plugin.getDescription().getName() + "already loaded"));
            return;
        }
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
