package com.erzbir.mirai.numeron.bot.codeprocess.runner;

import com.erzbir.mirai.numeron.bot.codeprocess.runway.RunCode;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:48
 */
public class CodeRunner extends AbstractRunner {
    private static final Object key = new Object();
    private static volatile CodeRunner INSTANCE;

    private CodeRunner() {

    }

    public static CodeRunner getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new CodeRunner();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public RunCode getRunCode() {
        return runCode;
    }

    @Override
    public void setRunCode(RunCode runCode) {
        this.runCode = runCode;
    }


}
