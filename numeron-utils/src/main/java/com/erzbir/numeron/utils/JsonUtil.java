package com.erzbir.numeron.utils;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Erzbir
 * @Date: 2023/3/4 19:04
 * <p>用于序列化和反序列话json文件</p>
 */
public class JsonUtil {
    public synchronized static <T> T load(String file, Class<T> tClass) throws ConfigReadException {
        Gson gson = new Gson();
        try {
            ConfigCreateUtil.createFile(file);
        } catch (IOException e) {
            throw new ConfigReadException(e);
        }
        File file1 = new File(file);
        if (!file1.exists()) {
            return null;
        }
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))) {
            return Files.size(Path.of(file1.toURI())) == 0 ? null : gson.fromJson(bufferedReader, tClass);
        } catch (FileNotFoundException e) {
            NumeronLogUtil.err("无配置文件" + e);
            throw new ConfigReadException(e);
        } catch (IOException e) {
            NumeronLogUtil.err("读取出错" + e);
            throw new ConfigReadException(e);
        }
    }

    public synchronized static void dump(String file, Object object, Class<?> type) throws ConfigWriteException {
        File file1 = new File(file);
        if (!file1.exists()) {
            return;
        }
        Gson gson = new Gson();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file1))) {
            gson.toJson(object, type, bufferedWriter);
        } catch (IOException e) {
            NumeronLogUtil.err("保存出错");
            throw new ConfigWriteException(e);
        }
    }
}