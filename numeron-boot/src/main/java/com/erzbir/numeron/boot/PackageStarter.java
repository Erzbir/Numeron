package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.context.DefaultAppContext;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.exception.ProcessorException;
import com.erzbir.numeron.utils.ClassScanner;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class PackageStarter extends AbstractStarter implements Starter {
    public PackageStarter(String packageName, ClassLoader classLoader) {
        super(packageName, classLoader);
    }

    @Override
    public void boot() {
        super.boot();
        ClassScanner scanner = new ClassScanner(packageName, classLoader, null);
        try {
            // 扫瞄实现了 Processor 接口的类
            scanner.scanWithSupperClass(Processor.class).forEach(e -> {
                Processor processor;
                try {
                    processor = (Processor) e.getDeclaredConstructor().newInstance();
                    DefaultAppContext.INSTANCE.addProcessor(processor);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ex) {
                    NumeronLogUtil.logger.error("ERROR", ex);
                    throw new ProcessorException(ex);
                }
                executor.submit(processor::onApplicationEvent);
            });
        } finally {
            executor.shutdown();
        }
    }
}
