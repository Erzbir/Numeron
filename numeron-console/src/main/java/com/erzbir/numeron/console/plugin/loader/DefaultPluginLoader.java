package com.erzbir.numeron.console.plugin.loader;

import com.erzbir.numeron.console.plugin.Plugin;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarFile;

/**
 * @author Erzbir
 * @Date 2023/11/5
 */

public class DefaultPluginLoader implements PluginLoader {
    public Set<Plugin> loadedPlugins = new HashSet<>();
    public Set<Class<?>> loadedClass = new HashSet<>();
    public URLClassLoader classLoader;
    String SERVICE_PATH = "META-INF/services/com.erzbir.numeron.console.plugin.Plugin";

    public DefaultPluginLoader(URLClassLoader urlClassLoader) {
        this.classLoader = urlClassLoader;
    }

    public DefaultPluginLoader() {
        this(null);
    }

    @Override
    public void load(File file) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            if (classLoader == null) {
                classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            }
        } catch (IOException e) {
            NumeronLogUtil.err(e.getMessage());
        }
        final JarFile finalJarFile = jarFile;
        if (finalJarFile == null) {
            return;
        }
        finalJarFile.entries().asIterator().forEachRemaining(jarEntry -> {
            String name = jarEntry.getName();
            if (name.endsWith(".class")) {
                String className = name.replaceAll("/", ".")
                        .replace(".class", "");
                try {
                    Class<?> aClass = classLoader.loadClass(className);
                    if (aClass != null) {
                        loadedClass.add(aClass);
                    }
                } catch (ClassNotFoundException e) {
                    NumeronLogUtil.err(e.getMessage());
                }
            }
        });
        loadJar(jarFile);
    }

    @Override
    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public void setClassLoader(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Set<Plugin> getLoadedPlugins() {
        return null;
    }

    @Override
    public Set<Class<?>> getClassCache() {
        return loadedClass;
    }

    private void loadJar(JarFile jarFile) {
        try (jarFile; BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jarFile.getInputStream(jarFile.getEntry(SERVICE_PATH))))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Class<?> clazz = Class.forName(line, true, classLoader);
                if (Plugin.class.isAssignableFrom(clazz)) {
                    Field instanceField = clazz.getField("INSTANCE");
                    instanceField.setAccessible(true);
                    Object instance = instanceField.get(null);
                    loadedPlugins.add((Plugin) instance);
                }
            }
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException | IOException e) {
            NumeronLogUtil.logger.error("ERROR", e);
        }
    }
}
