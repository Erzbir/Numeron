package com.erzbir.numeron.plugin.openai.config;

import com.erzbir.numeron.core.utils.ConfigCreateUtil;
import com.erzbir.numeron.core.utils.ConfigReadException;
import com.erzbir.numeron.core.utils.ConfigWriteException;
import com.erzbir.numeron.core.utils.JsonUtil;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:52
 */
public class ChatConfig implements Serializable {

    private static final Object key = new Object();
    private static final String configFile = "erzbirnumeron/plugin-configs/chatgpt/chat.json";
    private static volatile ChatConfig INSTANCE;
    private transient String model = "gpt-3.5-turbo";
    private int max_tokens = 512;
    private double temperature = 0.9;
    private double top_p = 1.0;
    private double presence_penalty = 0.6;
    private double frequency_penalty = 0.0;

    private ChatConfig() {
        try {
            ConfigCreateUtil.createFile(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChatConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = JsonUtil.load(configFile, ChatConfig.class);
                    } catch (ConfigReadException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new ChatConfig();
                    try {
                        JsonUtil.dump(configFile, INSTANCE, ChatConfig.class);
                    } catch (ConfigWriteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return INSTANCE;
        //  return new ChatConfig();
    }

    public ChatCompletionRequest load() {
        return ChatCompletionRequest.builder()
                .maxTokens(max_tokens)
                .model(model)
                .messages(new LinkedList<>())
                .presencePenalty(presence_penalty)
                .topP(top_p)
                .frequencyPenalty(frequency_penalty)
                .build();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMax_tokens() {
        return max_tokens;
    }

    public void setMax_tokens(int max_tokens) {
        this.max_tokens = max_tokens;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getTop_p() {
        return top_p;
    }

    public void setTop_p(double top_p) {
        this.top_p = top_p;
    }

    public double getPresence_penalty() {
        return presence_penalty;
    }

    public void setPresence_penalty(double presence_penalty) {
        this.presence_penalty = presence_penalty;
    }

    public double getFrequency_penalty() {
        return frequency_penalty;
    }

    public void setFrequency_penalty(double frequency_penalty) {
        this.frequency_penalty = frequency_penalty;
    }
}
