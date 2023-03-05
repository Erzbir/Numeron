package com.erzbir.mirai.numeron.plugins.openai;

import com.google.gson.Gson;

import java.io.*;

/**
 * @author Erzbir
 * @Date: 2023/3/4 19:04
 */
public class JsonUtil {
    public static <T> T load(String file, Class<T> tClass) {
        Gson gson = new Gson();
        File file1 = new File(file);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))) {
            return gson.fromJson(bufferedReader, tClass);
        } catch (FileNotFoundException e) {
            System.out.println("无配置文件");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
