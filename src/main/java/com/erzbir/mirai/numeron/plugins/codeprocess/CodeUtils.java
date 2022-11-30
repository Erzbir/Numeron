package com.erzbir.mirai.numeron.plugins.codeprocess;

import com.erzbir.mirai.numeron.config.GlobalConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Erzbir
 * @Date: 2022/11/30 09:13
 */
public class CodeUtils {

    public static String readResult(Process process, long timeout, String commandType) throws Exception {
        Charset use = StandardCharsets.UTF_8;

        if (Objects.equals(commandType, "sh") && GlobalConfig.OS.startsWith("Windows")) {
            use = Charset.forName("GBK");
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), use));
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream(), use));
        StringBuilder errBuffer = new StringBuilder();

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> submit = service.submit(() -> {
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    stringBuffer.append(line).append("\n");
                }
                while ((line = err.readLine()) != null) {
                    errBuffer.append(line).append("\n");
                }
            } catch (Exception e) {
                errBuffer.append(e.getMessage()).append("\n");
            }
        });

        try {
            submit.get(timeout == 0 ? 5 : timeout, TimeUnit.SECONDS);
        } catch (Exception ignored) {
            if (process.isAlive()) {
                process.destroyForcibly();
            }
        } finally {
            service.shutdown();
            in.close();
            err.close();
        }
        // 这里不能超过5000
        if (!errBuffer.isEmpty()) {
            throw new Exception(errBuffer.toString().trim());
        } else if (!stringBuffer.isEmpty()) {
            return stringBuffer.toString().trim();
        }
        return "该命令没有输出";
    }
}
