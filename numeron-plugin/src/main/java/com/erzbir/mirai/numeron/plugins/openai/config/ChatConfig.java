package com.erzbir.mirai.numeron.plugins.openai.config;

import com.erzbir.mirai.numeron.plugins.openai.JsonUtil;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:52
 */
public class ChatConfig implements Serializable {

    private String model = "gpt-3.5-turbo";
    private int max_tokens = 512;
    private double temperature = 0.9;
    private double top_p = 1.0;
    private double presence_penalty = 0.6;
    private double frequency_penalty = 0.0;

    public ChatConfig() {

    }

    public ChatConfig(String model, int maxTokens, double temperature, long timeout) {
        this.model = model;
        this.max_tokens = maxTokens;
        this.temperature = temperature;
    }

    public ChatConfig(String model, int maxTokens, double temperature, double topP, double presencePenalty, double frequencyPenalty, long timeout) {
        this.model = model;
        this.max_tokens = maxTokens;
        this.temperature = temperature;
        this.top_p = topP;
        this.presence_penalty = presencePenalty;
        this.frequency_penalty = frequencyPenalty;
    }

    public static ChatConfig getInstance() {
        return JsonUtil.load("erzbirnumeron/plugin-configs/chatgpt/chat.json", ChatConfig.class);
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
