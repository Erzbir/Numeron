package com.erzbir.numeron.test;

import com.erzbir.numeron.console.plugin.HotSpiPluginLoader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/19 14:24
 */
public class PluginHotLoadTest {
    private static List<Object> list = new ArrayList<>();

    @Test
    public void test1() {
        try {
            test2();
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

    public void test2() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Method m = HotSpiPluginLoader.class.getDeclaredMethod("load", File.class);
        m.setAccessible(true);
        HotSpiPluginLoader loader = new HotSpiPluginLoader();
        File file = new File("/Users/erzbir/IdeaProjects/untitled3/C.class");
        Class<?> load = (Class<?>) m.invoke(loader, file);
        Constructor<?> constructor = load.getConstructor();
        constructor.newInstance();
    }

    @Test
    public void test3() {
        try {
            Method m = HotSpiPluginLoader.class.getDeclaredMethod("load", File.class);
            m.setAccessible(true);
            HotSpiPluginLoader loader = new HotSpiPluginLoader();
            File file = new File("/Users/erzbir/IdeaProjects/Numeron/numeron_plugins/plugin-test1.0-jar-with-dependencies.jar");
            // Class<?> load = (Class<?>) m.invoke(loader, file);
            //Constructor<?> constructor = load.getConstructor();
            //constructor.newInstance();
            List<Object> x = loader.loadNewJar(file);
//        list.add(x);
            System.out.println(x);
            list = null;
            x = null;
            loader = null;
            System.gc();
//        Thread.sleep(1222);
            File file1 = new File("/Users/erzbir/IdeaProjects/Numeron/numeron_plugins/plugin-test1.0-jar-with-dependencies.jar");

            Method m1 = HotSpiPluginLoader.class.getDeclaredMethod("load", File.class);
            m1.setAccessible(true);
            HotSpiPluginLoader loader2 = new HotSpiPluginLoader();
            // Class<?> load2 = (Class<?>) m.invoke(loader2, file);
            // Constructor<?> constructor2 = load2.getConstructor();
            // constructor2.newInstance();
            System.out.println(loader2.loadNewJar(file1));
            loader2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() {
        try {
            Method m = HotSpiPluginLoader.class.getDeclaredMethod("load", File.class);
            m.setAccessible(true);
            HotSpiPluginLoader loader = new HotSpiPluginLoader();
            File file = new File("/Users/erzbir/IdeaProjects/Numeron/numeron_plugins/plugin-test1.0-jar-with-dependencies.jar");
            // Class<?> load = (Class<?>) m.invoke(loader, file);
            //Constructor<?> constructor = load.getConstructor();
            //constructor.newInstance();
            System.out.println(loader.loadNewJar(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
