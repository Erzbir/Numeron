package com.erzbir.mirai.numeron.plugins.openai.config;

import com.erzbir.mirai.numeron.plugins.openai.JsonUtil;
import com.theokanning.openai.completion.CompletionRequest;

import java.io.Serializable;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:54
 */
public class QuestionConfig implements Serializable {
    private String model = "text-davinci-003";
    private int max_tokens = 2048;
    private double temperature = 0.0;
    private double top_p = 1.0;
    private double presence_penalty;
    private double frequency_penalty;
    private int n = 1;

    public QuestionConfig() {

    }

    public QuestionConfig(String model, int maxTokens, double temperature, int number) {
        this.model = model;
        this.max_tokens = maxTokens;
        this.temperature = temperature;
        this.n = number;
    }

    public QuestionConfig(String model, int max_tokens, double temperature, double top_p, double presence_penalty, double frequency_penalty, int n) {
        this.model = model;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
        this.top_p = top_p;
        this.presence_penalty = presence_penalty;
        this.frequency_penalty = frequency_penalty;
        this.n = n;
    }

    public static QuestionConfig getInstance() {
        return JsonUtil.load("erzbirnumeron/plugin-configs/chatgpt/question.json", QuestionConfig.class);
        //return new QuestionConfig();
    }

    public CompletionRequest load() {
        return CompletionRequest.builder()
                .maxTokens(max_tokens)
                .model(model)
                .n(n)
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

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
