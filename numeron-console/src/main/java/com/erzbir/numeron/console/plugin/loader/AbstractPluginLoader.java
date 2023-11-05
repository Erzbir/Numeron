//package com.erzbir.numeron.console.plugin.loader;
//
//import com.erzbir.numeron.console.plugin.Plugin;
//
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author Erzbir
// * @Date 2023/11/3
// */
//public abstract class AbstractPluginLoader<E> extends ClassLoader implements PluginLoader<E> {
//    protected ClassLoader classLoader;
//    protected Set<Plugin> loadedPlugins = new HashSet<>();
//    protected Map<String, Class<?>> classCache = new ConcurrentHashMap<>();
//
//    public AbstractPluginLoader() {
//        super();
//    }
//
//    @Override
//    public Set<Plugin> getLoadedPlugins() {
//        return loadedPlugins;
//    }
//
//    @Override
//    public Map<String, Class<?>> getClassCache() {
//        return classCache;
//    }
//
//    public ClassLoader getClassLoader() {
//        if (classLoader == null) {
//            return this;
//        }
//        return classLoader;
//    }
//
//    public void setClassLoader(ClassLoader classLoader) {
//        this.classLoader = classLoader;
//    }
//}
