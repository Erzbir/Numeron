package com.erzbir.numeron.console.plugin;

import java.io.File;
import java.util.Set;

/**
 * @author Erzbir
 * @Date 2023/8/2
 */
public interface IPluginContext {
    Set<Class<?>> getClasses();

    Plugin getPlugin();

    File getOriginalFile();

    void destroy();

    ClassLoader getClassLoader();
}
