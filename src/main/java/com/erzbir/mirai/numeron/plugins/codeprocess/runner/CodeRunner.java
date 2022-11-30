package com.erzbir.mirai.numeron.plugins.codeprocess.runner;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:48
 */
public class CodeRunner extends AbstractRunner {
    private static final Object key = new Object();
    private static volatile CodeRunner INSTANCE;

    public CodeRunner() {
        super();
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
}
