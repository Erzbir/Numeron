package com.erzbir.numeron.console.plugin;


import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author Erzbir
 * @Date: 2023/6/19 17:36
 */
class HotSpiPluginLoaderTest {
    @Test
    public void hatLoadTest() {
        try {
            test();
            System.gc();
//        Thread.sleep(1222);
            File file = new File("/Users/erzbir/IdeaProjects/untitled3/C.class");
            Method m = HotSpiPluginLoader.class.getDeclaredMethod("load", File.class, String.class);
            m.setAccessible(true);
            HotSpiPluginLoader loader2 = new HotSpiPluginLoader();
            Class<?> load2 = (Class<?>) m.invoke(loader2, file, "C");
            Constructor<?> constructor2 = load2.getConstructor();
            constructor2.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Method m = HotSpiPluginLoader.class.getDeclaredMethod("load", File.class, String.class);
        m.setAccessible(true);
        HotSpiPluginLoader loader = new HotSpiPluginLoader();
        File file = new File("/Users/erzbir/IdeaProjects/untitled3/C.class");
        Class<?> load = (Class<?>) m.invoke(loader, file, "C");
        Constructor<?> constructor = load.getConstructor();
        constructor.newInstance();
    }

}