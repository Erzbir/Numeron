package com.erzbir.mirai.numeron.bot.codeprocess.runway;


import com.erzbir.mirai.numeron.bot.codeprocess.CodeUtil;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:17
 */
public class RunShell implements RunCode {
    private static final Object key = new Object();
    private static final String codeDir = "erzbirnumeron" + "/botCode/shell";
    private static volatile RunShell INSTANCE;

    public static RunShell getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new RunShell();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public String execute(String code) throws IOException {
        String id = UUID.randomUUID().toString().replace("-", "");
        String OS = System.getProperty("os.name");
        String executable;
        String filename;
        if (OS.startsWith("Windows")) {
            filename = id + ".bat";
            executable = "";
        } else {
            filename = id + ".sh";
            executable = "sh";
        }
        File file = CodeUtil.createCodeFile(codeDir, filename, code);
        return CodeUtil.execute(executable, filename, file);
    }
}
