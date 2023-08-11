package com.erzbir.numeron.core.handler.excute;

import com.erzbir.numeron.utils.NumeronLogUtil;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:32
 * <p>消息处理方法</p>
 */
public class EventMethodExecute implements MethodExecute {
    public static final EventMethodExecute INSTANCE = new EventMethodExecute();
    private Runnable before = () -> {
    };
    private Runnable after = () -> {
    };

    private EventMethodExecute() {
    }

    @Override
    public void executeBefore(Runnable runnable) {
        before = runnable == null ? before : runnable;
    }

    @Override
    public void executeAfter(Runnable runnable) {
        after = runnable == null ? before : runnable;
    }

    @Override
    public void execute(Method method, Object bean, Object... args) {
        try {
            before.run();
            method.setAccessible(true);
            method.invoke(bean, args);
            after.run();
        } catch (Exception e) {
            NumeronLogUtil.logger.error("ERROR", e);
        }
    }
}
