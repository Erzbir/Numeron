package com.erzbir.mirai.numeron.bot.openai.config;

import com.erzbir.mirai.numeron.bot.openai.JsonUtil;
import com.erzbir.mirai.numeron.entity.NumeronBot;

import java.io.File;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:53
 */
public class OpenAiConfig {
    public static final String dir = NumeronBot.INSTANCE.getWorkDir() + "plugin/chatgpt/";

    static {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private long timeout = 30L;
    private String token = "";
    private boolean reply = false;
    private boolean chat_by_at = false;
    private int limit = 20;

    public OpenAiConfig() {

    }

    public OpenAiConfig(long timeout, String token, boolean reply, boolean chatByAt, int limit) {
        this.timeout = timeout;
        this.token = token;
        this.reply = reply;
        this.chat_by_at = chatByAt;
        this.limit = limit;
    }

    public static OpenAiConfig getInstance() {
        return JsonUtil.load("erzbirnumeron/plugin-configs/chatgpt/openai.json", OpenAiConfig.class);
        //return new OpenAiConfig();
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public boolean isChat_by_at() {
        return chat_by_at;
    }

    public void setChat_by_at(boolean chat_by_at) {
        this.chat_by_at = chat_by_at;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDir() {
        return dir;
    }
}
