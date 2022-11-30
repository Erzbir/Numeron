package com.erzbir.mirai.numeron.processor.executor;

import com.erzbir.mirai.numeron.processor.excute.MethodExecute;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:34
 */
@Setter
@Getter
public abstract class AbstractMethodExecutor {
    private MethodExecute execute;

    public AbstractMethodExecutor() {

    }
}
