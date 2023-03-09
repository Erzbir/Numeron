package com.erzbir.mirai.numeron.utils;

import com.google.gson.Gson;

import java.io.*;

/**
 * @author Erzbir
 * @Date: 2023/3/4 19:04
 * <p>用于序列化和反序列话json文件</p>
 */
public class JsonUtil {
    public static <T> T load(String file, Class<T> tClass) {
        Gson gson = new Gson();
        ConfigCreateUtil.createFile(file);
        File file1 = new File(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))) {
            return gson.fromJson(bufferedReader, tClass);
        } catch (FileNotFoundException e) {
            MiraiLogUtil.err("无配置文件" + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            MiraiLogUtil.err("读取出错" + e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static boolean dump(String file, Object object, Class<?> type) {
        Gson gson = new Gson();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            gson.toJson(object, type, bufferedWriter);
        } catch (IOException e) {
            MiraiLogUtil.err("保存出错");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return true;
    }
}