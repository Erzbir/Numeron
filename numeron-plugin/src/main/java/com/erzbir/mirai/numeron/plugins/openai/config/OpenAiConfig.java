package com.erzbir.mirai.numeron.plugins.openai.config;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.utils.ConfigCreateUtil;
import com.erzbir.mirai.numeron.utils.JsonUtil;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:53
 */
public class OpenAiConfig {
    public static final String dir = NumeronBot.INSTANCE.getFolder() + "plugin/chatgpt/";
    private static final Object key = new Object();
    private static volatile OpenAiConfig INSTANCE;

    static {
        ConfigCreateUtil.createDir(dir);
    }

    private long timeout = 30L;
    private String token = "";
    private boolean reply = false;
    private boolean chat_by_at = false;
    private int limit = 20;

    private OpenAiConfig() {

    }

    public static OpenAiConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = JsonUtil.load("erzbirnumeron/plugin-configs/chatgpt/openai.json", OpenAiConfig.class);
                }
            }
        }
        return INSTANCE == null ? new OpenAiConfig() : INSTANCE;
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
