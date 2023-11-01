package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.exception.ProcessorException;
import com.erzbir.numeron.utils.ClassScanner;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.io.IOException;
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
        ClassScanner scanner = new ClassScanner(basePackage, classLoader, true, null);
        try {
            // 扫瞄实现了 Processor 接口的类
            scanner.scanWithInterface(Processor.class).forEach(e -> {
                Processor processor;
                try {
                    processor = (Processor) e.getDeclaredConstructor().newInstance();
                    AppContext.INSTANCE.addProcessor(processor);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                         NoSuchMethodException ex) {
                    NumeronLogUtil.logger.error("ERROR", ex);
                    throw new ProcessorException(ex);
                }
                executor.submit(processor::onApplicationEvent);
            });
        } catch (IOException | ClassNotFoundException e) {
            NumeronLogUtil.logger.error(e.getMessage(), e);
            throw new ProcessorException(e);
        } finally {
            executor.shutdown();
        }
    }
}
