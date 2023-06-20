package com.erzbir.numeron.plugin.openai.config;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.utils.ConfigReadException;
import com.erzbir.numeron.utils.ConfigWriteException;
import com.erzbir.numeron.utils.JsonUtil;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:53
 */
public class OpenAiConfig {
    private static final String configFile = NumeronImpl.INSTANCE.getPluginWorkDir() + "chatgpt/config/openai.json";
    private static final Object key = new Object();
    private static volatile OpenAiConfig INSTANCE;
    private long timeout = 30L;
    private String token = "";
    private boolean reply = false;
    private boolean chat_by_at = false;
    private int limit = 2048;

    private OpenAiConfig() {

    }

    public static OpenAiConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = JsonUtil.load(configFile, OpenAiConfig.class);
                    } catch (ConfigReadException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new OpenAiConfig();
                    try {
                        JsonUtil.dump(configFile, INSTANCE, OpenAiConfig.class);
                    } catch (ConfigWriteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return INSTANCE;
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
}
