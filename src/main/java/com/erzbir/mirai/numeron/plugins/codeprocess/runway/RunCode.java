package com.erzbir.mirai.numeron.plugins.codeprocess.runway;

import com.erzbir.mirai.numeron.configs.BotConfig;

import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:18
 */
public interface RunCode {
    String workDir = BotConfig.getWORKDIR();

    String execute(String code) throws IOException;
}
