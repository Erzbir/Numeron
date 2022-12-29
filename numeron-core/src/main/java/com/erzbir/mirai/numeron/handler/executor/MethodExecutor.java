package com.erzbir.mirai.numeron.handler.executor;

import com.erzbir.mirai.numeron.handler.excute.MethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/29 13:45
 * <p>具体处理器</p>
 */
public class MethodExecutor extends AbstractMethodExecutor {
    public final static MethodExecutor INSTANCE = new MethodExecutor();

    private MethodExecutor() {

    }

    public MethodExecute getExecute() {
        return execute;
    }

    public void setExecute(MethodExecute execute) {
        this.execute = execute;
    }
}
