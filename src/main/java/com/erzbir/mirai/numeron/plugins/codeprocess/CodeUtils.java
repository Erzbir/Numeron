package com.erzbir.mirai.numeron.plugins.codeprocess;

import com.erzbir.mirai.numeron.configs.GlobalConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
        } finally {
            if (process.isAlive()) {
                process.destroyForcibly();
            }
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
