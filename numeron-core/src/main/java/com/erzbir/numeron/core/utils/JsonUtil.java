package com.erzbir.numeron.core.utils;

import com.google.gson.Gson;

import java.io.*;

/**
 * @author Erzbir
 * @Date: 2023/3/4 19:04
 * <p>用于序列化和反序列话json文件</p>
 */
public class JsonUtil {
    public static <T> T load(String file, Class<T> tClass) throws ConfigReadException {
        Gson gson = new Gson();
        try {
            ConfigCreateUtil.createFile(file);
        } catch (IOException e) {
            throw new ConfigReadException(e);
        }
        File file1 = new File(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))) {
            return gson.fromJson(bufferedReader, tClass);
        } catch (FileNotFoundException e) {
            MiraiLogUtil.err("无配置文件" + e);
            throw new ConfigReadException(e);
        } catch (IOException e) {
            MiraiLogUtil.err("读取出错" + e);
            throw new ConfigReadException(e);
        }
    }

    public static void dump(String file, Object object, Class<?> type) throws ConfigWriteException {
        Gson gson = new Gson();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(object, type, bufferedWriter);
        } catch (IOException e) {
            MiraiLogUtil.err("保存出错");
            throw new ConfigWriteException(e);
        }
    }
}