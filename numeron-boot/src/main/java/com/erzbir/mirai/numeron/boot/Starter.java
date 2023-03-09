package com.erzbir.mirai.numeron.boot;

import com.erzbir.mirai.numeron.classloader.ClassScanner;
import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.processor.Processor;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2022/12/12 22:45
 */
public class Starter {
    public static void boot() {
        NumeronBot numeronBot = NumeronBot.INSTANCE;  // 这里是为了提前初始化, 不然会出现空指针异常
        // 包扫瞄器扫瞄所有class
        ClassScanner scanner = new ClassScanner(
                "com.erzbir.mirai.numeron",
                true,
                t -> true,
                null);
        try {
            /*
             * 扫瞄实现了{@code Processor}接口的类
             */
            scanner.scanWithInterface(Processor.class).forEach(e -> {
                try {
                    Processor processor = (Processor) e.getConstructor().newInstance();
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
