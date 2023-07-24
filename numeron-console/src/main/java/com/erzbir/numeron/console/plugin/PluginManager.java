package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.context.AppContextServiceImpl;
import com.erzbir.numeron.console.exception.PluginConflictException;
import com.erzbir.numeron.console.exception.PluginIllegalException;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2023/4/26 17:36
 */
public class PluginManager implements PluginService, PluginLoad, PluginManagerInter {
    private static final String pluginPath = "numeron_plugins/";
    public static PluginManager INSTANCE = new PluginManager();

    static {
        ConfigCreateUtil.createDir(pluginPath);
    }

    public final ExecutorService executor = Executors.newCachedThreadPool();
    private final Map<String, Plugin> pluginMap = new ConcurrentHashMap<>();
    private final Map<String, PluginContext> pluginPluginContextMap = new ConcurrentHashMap<>();

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

    @Override
    public void load(File plugin) {
        try (HotSpiPluginLoader hotSpiPluginLoader = new HotSpiPluginLoader()) {
            if (!plugin.getName().endsWith(".jar")) {
                throw new PluginIllegalException("not a legal plugin");
            }
            List<Object> objects = hotSpiPluginLoader.loadNewJar(plugin);
            objects.stream().filter(t -> Plugin.class.isAssignableFrom(t.getClass())).forEach(p -> {
                Plugin pl = (Plugin) p;
                Collection<Class<?>> values = hotSpiPluginLoader.getClassCache().values();
                PluginContext pluginContext = new PluginContext(pl, hotSpiPluginLoader, plugin, new HashSet<>(values));
                pluginPluginContextMap.put(pl.getClass().getName(), pluginContext);
                loadPlugin(pl);
            });
        } catch (Exception e) {
            NumeronLogUtil.logger.error("ERROR", new PluginIllegalException(e));
        }
    }

    @Override
    public void reload(Plugin plugin) {
        PluginContext pluginContext = pluginPluginContextMap.get(plugin.getClass().getName());
        File file = pluginContext.getFile();
        removePlugin(plugin);
        load(file);
    }

    @Override
    public void enable(Plugin plugin) {
        if (plugin.isEnable()) {
            NumeronLogUtil.logger.error("Plugin" + plugin.getDescription().getName() + "is already enabled and cannot be re-enabled.");
            return;
        }
        PluginContext pluginContext = pluginPluginContextMap.get(plugin.getClass().getName());
        AppContextServiceImpl.INSTANCE.addAllToContext(pluginContext.getClasses());
        BotServiceImpl.INSTANCE.shutdownAll();
        BotServiceImpl.INSTANCE.launchAll();
        plugin.enable();
    }

    @Override
    public void disable(Plugin plugin) {
        if (!plugin.isEnable()) {
            NumeronLogUtil.logger.error("Plugin" + plugin.getDescription().getName() + "is already disabled and cannot be re-disabled.");
            return;
        }
        PluginContext pluginContext = pluginPluginContextMap.get(plugin.getClass().getName());
        pluginContext.getClasses().forEach(AppContextServiceImpl.INSTANCE::removeContext);
        BotServiceImpl.INSTANCE.shutdownAll();
        BotServiceImpl.INSTANCE.launchAll();
        plugin.disable();
    }

    @Override
    public void removePlugin(Plugin plugin) {
        String name = plugin.getClass().getName();
        pluginMap.remove(name).onUnLoad();
        PluginContext pluginContext = pluginPluginContextMap.remove(name);
        pluginContext.unLoadPlugin();
        pluginContext = null;
        BotServiceImpl.INSTANCE.shutdownAll();
        System.gc();
        BotServiceImpl.INSTANCE.launchAll();
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
    public boolean isLoaded(Plugin plugin) {
        return pluginMap.containsValue(plugin);
    }

    @Override
    public void loadPlugin(Plugin plugin) {
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
