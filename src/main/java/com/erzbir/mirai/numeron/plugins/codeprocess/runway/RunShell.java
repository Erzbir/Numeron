package com.erzbir.mirai.numeron.plugins.codeprocess.runway;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.plugins.codeprocess.CodeUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

/**
 * @author Erzbir
 * @Date: 2022/11/30 08:17
 */
public class RunShell extends RunCode {
    private static final Object key = new Object();
    private static volatile RunShell INSTANCE;
    private static final String codeDir = GlobalConfig.HOME + "botCode/shell";

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
        File file;
        if (!(file = new File(codeDir)).exists()) {
            file.mkdirs();
        }
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
        Path of = Path.of(codeDir, filename);
        try (FileWriter fileWriter = new FileWriter(of.toFile())) {
            fileWriter.write(code);
            fileWriter.flush();
        }
        Process process;
        String result;
        try {
            process = Runtime.getRuntime().exec(String.format("%s %s", executable, of), null, file);
            result = CodeUtils.readResult(process, 30, "sh");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Files.delete(of);
        }
        return result;
    }
}
