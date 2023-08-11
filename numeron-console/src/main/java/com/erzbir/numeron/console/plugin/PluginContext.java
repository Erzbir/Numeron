package com.erzbir.numeron.console.plugin;

import com.erzbir.numeron.api.context.AppContextServiceImpl;
import com.erzbir.numeron.utils.CoroutineScopeBridge;

import java.io.File;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/16 09:58
 */
public class PluginContext implements IPluginContext {
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

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override

    public Plugin getPlugin() {
        return plugin;
    }

    @Override
    public File getOriginalFile() {
        return file;
    }

    @Override
    public void destroy() {
        CoroutineScopeBridge.Companion.cancel(plugin);
        classes.forEach(t -> {
            ClassLoader clad = t.getClassLoader();
            clad = null;
            AppContextServiceImpl.INSTANCE.removeBean(t);
            t = null;
        });
        classes.clear();
        classLoader = null;
        plugin = null;
        file = null;
        classes = null;
        System.gc();
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }
}