package com.erzbir.mirai.numeron.bot.codeprocess.runway;


import com.erzbir.mirai.numeron.bot.codeprocess.CodeUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:17
 */
public class RunPy implements RunCode {
    private static final Object key = new Object();
    private static final String codeDir = "erzbirnumeron" + "/botCode/py";
    private static volatile RunPy INSTANCE;

    public static RunPy getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new RunPy();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String execute(String code) throws IOException {
        String filename = UUID.randomUUID().toString().replace("-", "") + ".py";
        File file = CodeUtil.createCodeFile(codeDir, filename, code);
        return CodeUtil.execute("python3", filename, file);
    }
}
