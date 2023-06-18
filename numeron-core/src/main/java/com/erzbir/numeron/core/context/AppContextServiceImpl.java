package com.erzbir.numeron.core.context;

import com.erzbir.numeron.api.context.AppContextService;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Erzbir
 * @Date: 2023/6/17 23:27
 */
public class AppContextServiceImpl implements AppContextService {
    @Override
    public void addAllToContext(String packageName, ClassLoader classLoader, Class<? extends Annotation> annotation) {
        AppContext.INSTANCE.addAllToContext(packageName, classLoader, annotation);
    }

    @Override
    public void addToContext(Class<?> bean) {
        try {
            AppContext.INSTANCE.addToContext(bean);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            NumeronLogUtil.logger.error("ERROR", e);
        }
    }
}
