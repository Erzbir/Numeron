package com.erzbir.mirai.numeron.bot.openai;

/**
 * @author Erzbir
 * @Date: 2023/3/4 00:28
 */
public class Usage {
    public int completionTokens;
    public int promptTokens;
    public int totalTokens;

    public Usage() {

    }

    public Usage(int completionTokens, int promptTokens, int totalTokens) {
        this.completionTokens = completionTokens;
        this.promptTokens = promptTokens;
        this.totalTokens = totalTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(int completionTokens) {
        this.completionTokens = completionTokens;
    }

    public int getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(int promptTokens) {
        this.promptTokens = promptTokens;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }
}
