package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.api.context.AppContextServiceImpl;

import java.io.File;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/16 09:58
 */
public class PluginContext {
    private Set<Class<?>> classes;
    private Plugin plugin;

    private ClassLoader classLoader;

    private File file;

    public PluginContext(Plugin plugin, ClassLoader classLoader, File file, Set<Class<?>> classes) {
        this.plugin = plugin;
        this.classes = classes;
        this.file = file;
        this.classLoader = classLoader;
    }

    public Set<Class<?>> getClasses() {
        return classes;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public File getFile() {
        return file;
    }

    public void unLoadPlugin() {
        classes.forEach(AppContextServiceImpl.INSTANCE::removeContext);
        classes.clear();
        classLoader = null;
        plugin = null;
        file = null;
        classes = null;
        System.gc();
    }
}