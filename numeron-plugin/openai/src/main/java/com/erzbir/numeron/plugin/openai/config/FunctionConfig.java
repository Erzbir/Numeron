package com.erzbir.numeron.plugin.openai.config;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.utils.*;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Erzbir
 * @Date: 2023/6/19 20:10
 */
public class FunctionConfig implements Serializable {
    private static final Object key = new Object();
    private static final String configFile = NumeronImpl.INSTANCE.getPluginWorkDir() + "chatgpt/config/function.json";
    private static volatile FunctionConfig INSTANCE;
    private String model = "gpt-3.5-turbo-16k-0613";
    private int max_tokens = 512;
    private double temperature = 0.9;
    private double top_p = 1.0;
    private double presence_penalty = 0.6;
    private double frequency_penalty = 0.0;
    private String function_call = "auto";

    private FunctionConfig() {
        try {
            ConfigCreateUtil.createFile(configFile);
        } catch (IOException e) {
            NumeronLogUtil.logger.error("ERROR", e);
        }
    }

    public static FunctionConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = JsonUtil.load(configFile, FunctionConfig.class);
                    } catch (ConfigReadException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = new FunctionConfig();
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

    public String getFunction_call() {
        return function_call;
    }

    public void setFunction_call(String function_call) {
        this.function_call = function_call;
    }
}
