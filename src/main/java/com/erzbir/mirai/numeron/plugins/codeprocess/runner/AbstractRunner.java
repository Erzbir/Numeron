package com.erzbir.mirai.numeron.plugins.codeprocess.runner;

import com.erzbir.mirai.numeron.plugins.codeprocess.runway.RunCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:20
 */
@Getter
@Setter
public abstract class AbstractRunner {
    protected RunCode runCode;

    public AbstractRunner() {

    }
}
