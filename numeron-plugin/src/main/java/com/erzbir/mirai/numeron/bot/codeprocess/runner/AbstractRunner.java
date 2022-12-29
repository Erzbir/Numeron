package com.erzbir.mirai.numeron.bot.codeprocess.runner;

import com.erzbir.mirai.numeron.bot.codeprocess.runway.RunCode;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:20
 */
public abstract class AbstractRunner {
    protected RunCode runCode;

    protected abstract RunCode getRunCode();

    protected abstract void setRunCode(RunCode runCode);
}
