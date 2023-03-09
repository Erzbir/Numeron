package com.erzbir.mirai.numeron.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2023/3/6 13:24
 * <p>用于创建文件和目录</p>
 */
public class ConfigCreateUtil {
    public static void createFile(String confFile) {
        File file = new File(confFile);
        if (!file.isFile()) {
            try {
                if (file.createNewFile()) {
                    return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
