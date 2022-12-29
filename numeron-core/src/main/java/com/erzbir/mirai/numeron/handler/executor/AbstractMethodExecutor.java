package com.erzbir.mirai.numeron.handler.executor;

import com.erzbir.mirai.numeron.handler.excute.MethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:34
 * <p>抽象处理器</p>
 */
public abstract class AbstractMethodExecutor {
    protected MethodExecute execute;

    public abstract MethodExecute getExecute();

    public abstract void setExecute(MethodExecute execute);
}
