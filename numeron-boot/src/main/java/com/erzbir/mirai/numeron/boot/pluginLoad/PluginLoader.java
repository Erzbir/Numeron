package com.erzbir.mirai.numeron.boot.pluginLoad;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.jar.JarException;

public class PluginLoader {
    private final static String PROPERTIES_NAME = "numeron.plugin.properties";
    private final static String MAIN_CLASS = "mainClass";

    /**
     * @param jarFilePath jar文件路径
     * @return 返回加载的主类
     */
    public static Class<?> loadJar(String jarFilePath) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = getClassLoader(jarFilePath);
        if (classLoader == null) {
            throw new JarException("Not found this jar");
        }
        Properties properties = getProperties(classLoader);
        String mainClass = properties.getProperty(MAIN_CLASS);
        return loadClass(classLoader, mainClass);
    }

    /**
     * @param jarFilePath jar文件路径
     * @return 返回加载器
     */
    private static ClassLoader getClassLoader(String jarFilePath) throws MalformedURLException {
        File jarFile = new File(jarFilePath);
        if (!jarFile.exists()) {
            return null;
        }
        URL url = jarFile.toURI().toURL();
        return new URLClassLoader(new URL[]{url}, null);
    }

    /**
     * @param classLoader classLoader
     * @return 返回加载的properties
     */
    private static Properties getProperties(ClassLoader classLoader) throws IOException {
        InputStream propertiesStream = classLoader.getResourceAsStream(PluginLoader.PROPERTIES_NAME);
        Properties properties = new Properties();
        properties.load(propertiesStream);
        if (propertiesStream != null) {
            propertiesStream.close();
        }
        return properties;
    }

    /**
     * @param classLoader classLoader
     * @param className   全类名
     * @return 返回加载的主类
     */
    private static Class<?> loadClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
        return classLoader.loadClass(className);
    }

}

