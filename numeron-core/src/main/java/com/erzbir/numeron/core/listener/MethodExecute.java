package com.erzbir.numeron.core.listener;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:25
 * <p>消息处理接口</p>
 */
public interface MethodExecute {
    /**
     * @param method 要执行的方法
     * @param bean   包含 {@param method} 的实例
     * @param args   参数
     */
    void execute(Method method, Object bean, Object... args);
}
