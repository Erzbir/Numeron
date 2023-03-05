package com.erzbir.mirai.numeron.plugins.openai.config;

import com.erzbir.mirai.numeron.plugins.openai.JsonUtil;
import com.theokanning.openai.completion.CompletionRequest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Erzbir
 * @Date: 2023/3/3 23:52
 */
public class CompletionConfig implements Serializable {
    private String model = "text-davinci-003";
    private int max_tokens = 1024;
    private double temperature = 1.0;
    private double top_p = 1.0;
    private double presence_penalty = 0.0;
    private double frequency_penalty = 0.0;
    private int number = 1;
    private boolean echo = false;
    private List<String> stop = null;
    private int best_of = 1;
    private Map<String, Integer> logit_bias = null;
    private String suffix = null;

    public CompletionConfig() {

    }

    public CompletionConfig(String model, int max_tokens, double temperature, double top_p, double presence_penalty, double frequency_penalty, int number, boolean echo, List<String> stop, int best_of, Map<String, Integer> logit_bias, String suffix) {
        this.model = model;
        this.max_tokens = max_tokens;
        this.temperature = temperature;
        this.top_p = top_p;
        this.presence_penalty = presence_penalty;
        this.frequency_penalty = frequency_penalty;
        this.number = number;
        this.echo = echo;
        this.stop = stop;
        this.best_of = best_of;
        this.logit_bias = logit_bias;
        this.suffix = suffix;
    }

    public static CompletionConfig getInstance() {
        return JsonUtil.load("erzbirnumeron/plugin-configs/chatgpt/completion.json", CompletionConfig.class);
        //return new CompletionConfig();
    }

    public CompletionRequest load() {
        return CompletionRequest.builder()
                .maxTokens(max_tokens)
                .model(model)
                .n(number)
                .presencePenalty(presence_penalty)
                .topP(top_p)
                .frequencyPenalty(frequency_penalty)
                .bestOf(best_of)
                .logitBias(logit_bias)
                .suffix(suffix)
                .echo(echo)
                .suffix(suffix)
                .stop(stop)
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEcho() {
        return echo;
    }

    public void setEcho(boolean echo) {
        this.echo = echo;
    }

    public List<String> getStop() {
        return stop;
    }

    public void setStop(List<String> stop) {
        this.stop = stop;
    }

    public int getBest_of() {
        return best_of;
    }

    public void setBest_of(int best_of) {
        this.best_of = best_of;
    }

    public Map<String, Integer> getLogit_bias() {
        return logit_bias;
    }

    public void setLogit_bias(Map<String, Integer> logit_bias) {
        this.logit_bias = logit_bias;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
