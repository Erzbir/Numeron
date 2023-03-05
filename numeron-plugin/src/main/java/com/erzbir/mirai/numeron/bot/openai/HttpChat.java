package com.erzbir.mirai.numeron.bot.openai;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Erzbir
 * @Date: 2023/3/2 11:07
 */

public class HttpChat {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static Request.Builder requestBuilder;
    private static JSONObject postJson;

    static {
        String dirS = NumeronBot.INSTANCE.getWorkDir() + "plugin-configs/chatgpt/";
        File dir = new File(dirS);
        File file = new File(dirS + "chat.properties");
        try {
            if (!dir.exists() || !file.exists()) {
                dir.mkdirs();
                file.createNewFile();
            }
            Properties properties = new Properties();
            properties.load(new FileReader(file));
            String API_KEY = properties.getProperty("API_KEY");
            String API_URL = "https://api.openai.com/v1/completions";
            String model = "text-davinci-003";
            int max_tokens = Integer.parseInt(properties.getProperty("max_tokens"));
            double temperature = Double.parseDouble(properties.getProperty("temperature"));
            double top_p = Double.parseDouble(properties.getProperty("top_p"));
            String stop = properties.getProperty("stop");
            boolean stream = Boolean.getBoolean(properties.getProperty("stream"));
            int n = Integer.parseInt(properties.getProperty("n"));
            requestBuilder = new Request.Builder().url(API_URL);
            requestBuilder.addHeader("Content-Type", "application/json");
            requestBuilder.addHeader("Authorization", "Bearer " + API_KEY);
            postJson = new JSONObject();
            postJson.put("model", model);
            postJson.put("max_tokens", Integer.valueOf(max_tokens));
            postJson.put("temperature", temperature);
            postJson.put("top_p", top_p);
            postJson.put("stream", stream);
            postJson.put("stop", stop);
            postJson.put("n", n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Message(filterRule = FilterRule.BLACK, messageRule = MessageRule.BEGIN_WITH, permission = PermissionType.ALL, text = "t")
    private void chat(MessageEvent event) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS).build();
        String content = event.getMessage().contentToString().replaceFirst("t", "");
        String model = postJson.getString("model");
        String user = event.getSenderName();
        postJson.put("user", user);
        if (model.equals("gpt-3.5-turbo")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("role", "user");
            jsonObject.put("content", content);
            JSONArray jsonArray = new JSONArray().put(0, jsonObject);
            postJson.put("messages", jsonArray);
        } else if (model.equals("text-davinci-003")) {
            postJson.put("prompt", content);
        }
        RequestBody requestBody = RequestBody.create(postJson.toString(), JSON);
        requestBuilder.post(requestBody);
        try (Response response = client.newCall(requestBuilder.build()).execute()) {
            if (response.code() != 200) {
                return;
            }
            JSONObject responseJson = new JSONObject(response.body().string());
            String text = "";
            if (model.equals("gpt-3.5-turbo")) {
                text = responseJson.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");
            } else if (model.equals("text-davinci-003")) {
                text = responseJson.getJSONArray("choices").getJSONObject(0).getString("text");
            }
            text = text.replaceFirst("\\n\\n", "").replaceFirst("？", "");
            event.getSubject().sendMessage(text);
            System.out.println(text);
        }
    }
}
