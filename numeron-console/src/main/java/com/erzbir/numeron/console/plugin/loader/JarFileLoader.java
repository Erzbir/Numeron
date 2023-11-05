//package com.erzbir.numeron.console.plugin.loader;
//
//import com.erzbir.numeron.console.plugin.Plugin;
//import com.erzbir.numeron.utils.NumeronLogUtil;
//
//import java.io.*;
//import java.lang.reflect.Field;
//import java.util.jar.JarFile;
//
///**
// * @author Erzbir
// * @Date 2023/11/3
// */
//public class JarFileLoader extends AbstractFileLoader implements FilePluginLoader {
//
//
//    @Override
//    public void load(File file) {
//        JarFile jarFile = null;
//        try {
//            jarFile = new JarFile(file);
//        } catch (IOException e) {
//            NumeronLogUtil.err(e.getMessage());
//        }
//        final JarFile finalJarFile = jarFile;
//        if (finalJarFile == null) {
//            return;
//        }
//        finalJarFile.entries().asIterator().forEachRemaining(jarEntry -> {
//            String name = jarEntry.getName();
//            if (name.endsWith(".class") && !name.endsWith("module-info.class") && !name.endsWith("package-info.class")) {
//                InputStream inputStream;
//                String className = name.replaceAll("/", ".")
//                        .replace(".class", "");
//                try {
//                    inputStream = finalJarFile.getInputStream(jarEntry);
//                    if (findClass(className) == null && !classCache.containsKey(className)) {
//                        load(inputStream, className);
//                    }
//                } catch (IOException e) {
//                    NumeronLogUtil.err(e.getMessage());
//                }
//            }
//        });
//        loadJar(jarFile);
//    }
//
//    private void loadJar(JarFile jarFile) {
//        try (jarFile; BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(jarFile.getEntry(PATH))))) {
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                Class<?> clazz = Class.forName(line, true, this);
//                if (Plugin.class.isAssignableFrom(clazz)) {
//                    Field instanceField = clazz.getField("INSTANCE");
//                    instanceField.setAccessible(true);
//                    Object instance = instanceField.get(null);
//                    loadedPlugins.add((Plugin) instance);
//                }
//            }
//        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException | IOException e) {
//            NumeronLogUtil.logger.error("ERROR", e);
//        }
//    }
//
//    @Override
//    protected Object getClassLoadingLock(String className) {
//        return this;
//    }
//}
