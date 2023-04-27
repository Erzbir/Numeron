//package com.erzbir.numeron.console.plugin;
//
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author Erzbir
// * @Date: 2023/4/26 19:13
// */
//public class PluginLoader extends URLClassLoader {
//    private final Map<String, Class<?>> loadedClasses;
//
//
//    public PluginLoader(URL[] urls, ClassLoader parent) {
//        super(urls, parent);
//        loadedClasses = new ConcurrentHashMap<>();
//    }
//
//    public Plugin loadPlugin(String className) throws Exception {
//        Class<?> clazz = loadClass(className);
//        Object instance = clazz.getDeclaredField("INSTANCE").get(null);
//        Plugin plugin = (Plugin) instance;
//        plugin.onLoad();
//        return plugin;
//    }
//
//    @Override
//    protected synchronized Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
//        // 检查已经加载过的类
//        Class<?> clazz = loadedClasses.get(className);
//        if (clazz != null) {
//            return clazz;
//        }
//        // 委托给父类加载器进行加载
//        try {
//            clazz = loadClass(className);
//        } catch (ClassNotFoundException e) {
//            // 如果父类加载器无法加载，则由本类加载器进行加载
//            clazz = super.findClass(className);
//        }
//        // 缓存已加载的类
//        loadedClasses.put(className, clazz);
//        // 解析类
//        if (resolve) {
//            resolveClass(clazz);
//        }
//        return clazz;
//    }
//}
