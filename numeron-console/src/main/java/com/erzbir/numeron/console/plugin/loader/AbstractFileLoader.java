//package com.erzbir.numeron.console.plugin.loader;
//
//import com.erzbir.numeron.utils.NumeronLogUtil;
//import org.apache.commons.io.IOUtils;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * @author Erzbir
// * @Date 2023/11/3
// */
//public abstract class AbstractFileLoader extends AbstractPluginLoader<File> implements FilePluginLoader {
//
//    @Override
//    public Class<?> findClass(String className) {
//        Class<?> aClass = classCache.get(className);
//        if (aClass == null) {
//            try {
//                aClass = super.findClass(className);
//            } catch (ClassNotFoundException ignore) {
//            }
//        }
//        return aClass;
//    }
//
//    @Override
//    public Class<?> loadClass(String className) {
//        synchronized (getClassLoadingLock(className)) {
//            Class<?> c;
//            c = classCache.get(className);
//            try {
//                if (c == null) {
//                    c = findLoadedClass(className);
//                }
//                if (c == null) {
//                    c = findClass(className);
//                }
//                if (c == null) {
//                    if (classLoader != null) {
//                        classLoader.loadClass(className);
//                    } else {
//                        c = getSystemClassLoader().loadClass(className);
//                    }
//                }
//            } catch (ClassNotFoundException e) {
//                return null;
//            }
//            if (c != null) {
//                classCache.put(className, c);
//            }
//            return c;
//        }
//    }
//
//    protected Class<?> load(InputStream inputStream, String className) throws IOException {
//        final byte[] bytes = inputStream.readAllBytes();
//        return load(className, bytes);
//    }
//
//    protected Class<?> load(File file, String className) {
//        byte[] bytes = fileToBytes(file);
//        return load(className, bytes);
//    }
//
//    protected Class<?> load(String className, byte[] bytes) {
//        try {
//            Class<?> defineClass = defineClass(className, bytes, 0, bytes.length);
//            if (defineClass != null) {
//                classCache.put(className, defineClass);
//            }
//            return defineClass;
//        } catch (IllegalAccessError | NoClassDefFoundError e) {
//            loadClass(className);
//        }
//        return null;
//    }
//
//
//    protected byte[] fileToBytes(File file) {
//        try {
//            return IOUtils.toByteArray(file.toURI());
//        } catch (IOException e) {
//            NumeronLogUtil.logger.error("ERROR", e);
//            return new byte[0];
//        }
//    }
//
//    protected String fileToClassName(File file) throws IOException {
//        String path = file.getCanonicalPath();
//        String className = path.substring(path.lastIndexOf("/classes") + 9);
//        return className.replaceAll("/", ".").replace(".class", "");
//    }
//}
