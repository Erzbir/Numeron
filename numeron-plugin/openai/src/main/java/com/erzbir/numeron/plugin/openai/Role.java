package com.erzbir.numeron.plugin.openai;

/**
 * @author Erzbir
 * @Date: 2023/6/19 21:34
 */
public class Role {
    private String name = "";
    private String prompt = "";

    public Role() {

    }

    public Role(String name, String prompt) {
        this.name = name;
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String toString() {
        return "name: " + name + '\n' +
                "prompt: " + prompt + '\n';
    }
}
