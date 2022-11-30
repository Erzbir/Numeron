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
public class RunPy extends RunCode {
    private static final Object key = new Object();
    private static volatile RunPy INSTANCE;
    private static final String codeDir = GlobalConfig.HOME + "/botCode/py";

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
        File file;
        if (!(file = new File(codeDir)).exists()) {
            file.mkdirs();
        }
        String id = UUID.randomUUID().toString().replace("-", "");
        String filename = id + ".py";
        Path of = Path.of(codeDir, filename);
        try (FileWriter fileWriter = new FileWriter(of.toFile())) {
            fileWriter.write(code);
            fileWriter.flush();
        }
        String r = null;
        Process process;
        try {
            process = Runtime.getRuntime().exec(String.format("python3.11 %s", filename), null, file);
            r = CodeUtils.readResult(process, 30, "py");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Files.delete(of);
        }
        return r;
    }
}
