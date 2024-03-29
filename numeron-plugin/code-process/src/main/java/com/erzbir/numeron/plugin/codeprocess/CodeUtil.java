package com.erzbir.numeron.plugin.codeprocess;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2022/11/30 09:13
 */
public class CodeUtil {
    private static final ExecutorService ex = Executors.newSingleThreadExecutor();

    private static Charset getCharset() {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return Charset.forName("GBK");
        }
        return StandardCharsets.UTF_8;
    }

    private static StringBuilder readResultS(BufferedReader in) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try (in) {
            while ((line = in.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            stringBuilder.append(e.getMessage()).append("\n");
        }
        return stringBuilder;
    }


    private static String readResult(Process process) throws CodeReadOverException {
        Charset use = getCharset();
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder errBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), use)); BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream(), use))) {
            stringBuilder = readResultS(in);
            errBuilder = readResultS(err);
        } catch (Exception e) {
            errBuilder.append(e.getMessage()).append("\n");
        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();
            }
        }
        // 这里不能超过5000
        if (!errBuilder.isEmpty()) {
            throw new CodeReadOverException(errBuilder.toString().trim());
        } else if (!stringBuilder.isEmpty()) {
            return stringBuilder.toString().trim();
        }
        return "该命令没有输出";
    }

    public static String execute(String type, String filename, File file) {
        String result;
        try {
            result = CodeUtil.readResult(Runtime.getRuntime().exec(type + " " + filename, null, file.getParentFile()));
        } catch (CodeReadOverException | IOException e) {
            result = e.getMessage();
        } finally {
            ex.submit(() -> {
                try {
                    Files.delete(file.getAbsoluteFile().toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return result;
    }

    public static File createCodeFile(String dir, String filename, String code) throws IOException {
        File file;
        if (!(file = new File(dir)).exists()) {
            if (!file.mkdirs()) {
                throw new IOException("创建文件夹失败");
            }
        }
        file = Path.of(dir, filename).toFile();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(code);
            fileWriter.flush();
        }
        return file;
    }

}
