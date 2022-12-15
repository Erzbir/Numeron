package com.erzbir.mirai.numeron.boot.plugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/12/16 00:15
 */
public class PluginMethodHandle {
    public static void execute() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        String jarFilePath = "/Users/erzbir/IdeaProjects/untitled7/target/untitled7-1.0-SNAPSHOT.jar";
        Class<?> mainClass = PluginLoader.loadJar(jarFilePath);
        Method enable = mainClass.getMethod("onEnable");
        Constructor<?> declaredConstructor = mainClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Object o = declaredConstructor.newInstance();
        enable.invoke(o);
    }

    //TODO: 扫瞄 相应注解标注的方法/实现了对应接口的类
}
