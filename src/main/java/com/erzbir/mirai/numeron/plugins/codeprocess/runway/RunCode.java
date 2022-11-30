package com.erzbir.mirai.numeron.plugins.codeprocess.runway;

import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:18
 */
public abstract class RunCode {
    public abstract String execute(String code) throws IOException;
}
