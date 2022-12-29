package com.erzbir.mirai.numeron.boot;

import com.erzbir.mirai.numeron.boot.classloader.ClassScanner;
import com.erzbir.mirai.numeron.boot.processor.Processor;
import com.erzbir.mirai.numeron.entity.NumeronBot;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/12/12 22:45
 */
public class Starter {
    public static void boot() {
        ClassScanner scanner = new ClassScanner("com.erzbir.mirai.numeron", true, t -> true, null);
        try {
            scanner.scanWithInterface(Processor.class).forEach(e -> {
                try {
                    Processor processor = e.getConstructor().newInstance();
                    processor.onApplicationEvent();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ex) {
                    throw new RuntimeException(ex);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        NumeronBot.INSTANCE.getBot().login();
    }
}
