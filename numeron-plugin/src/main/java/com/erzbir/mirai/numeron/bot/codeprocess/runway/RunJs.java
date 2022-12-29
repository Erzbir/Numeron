package com.erzbir.mirai.numeron.bot.codeprocess.runway;


import com.erzbir.mirai.numeron.bot.codeprocess.CodeUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:17
 */
public class RunJs implements RunCode {
    private static final Object key = new Object();
    private static final String codeDir = "erzbirnumeron" + "/botCode/js";
    private static volatile RunJs INSTANCE;

    public static RunJs getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new RunJs();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String execute(String code) throws IOException {
        String filename = UUID.randomUUID().toString().replace("-", "") + ".js";
        File file = CodeUtil.createCodeFile(codeDir, filename, code);
        return CodeUtil.execute("node", filename, file);
    }
}