//package com.erzbir.numeron.console.plugin.loader;
//
//import com.erzbir.numeron.console.plugin.Plugin;
//import com.erzbir.numeron.utils.NumeronLogUtil;
//
//import java.io.File;
//import java.io.IOException;
//
///**
// * @author Erzbir
// * @Date 2023/11/3
// */
//public class ClassFileLoader extends AbstractFileLoader implements FilePluginLoader {
//
//    public void load(File file) {
//        String className;
//        try {
//            className = fileToClassName(file);
//            if (findClass(className) == null && !classCache.containsKey(className)) {
//                return;
//            }
//            Class<?> load = load(file, className);
//            if (Plugin.class.isAssignableFrom(load)) {
//                loadedPlugins.add((Plugin) load.getDeclaredField("INSTANCE").get(load));
//            }
//        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
//            NumeronLogUtil.err(e.getMessage());
//        }
//
//    }
//}
