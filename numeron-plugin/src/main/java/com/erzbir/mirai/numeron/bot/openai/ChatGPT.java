package com.erzbir.mirai.numeron.bot.openai;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Erzbir
 * @Date: 2023/3/3 18:16
 */
public class ChatGPT {
    private static final List<ChatMessage> conversation = new LinkedList<>();
    private static int count = 0;
    private static String model;
    private static int max_tokens;
    private static double temperature;
    private static double top_p;
    private static boolean stream;
    private static int num;
    private static double frequency_penalty;
    private static double presence_penalty;
    private static OpenAiService service;

    static {
        try {
            readConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readConfig() throws IOException {
        String dirS = NumeronBot.INSTANCE.getWorkDir() + "plugin-configs/chatgpt/";
        File dir = new File(dirS);
        File file = new File(dirS + "chat.properties");
        if (!dir.exists() || !file.exists()) {
            dir.mkdirs();
            file.createNewFile();
        }
        Properties properties = new Properties();
        properties.load(new FileReader(file));
        String API_KEY = properties.getProperty("API_KEY");
        model = properties.getProperty("model");
        max_tokens = Integer.parseInt(Objects.requireNonNullElse(properties.getProperty("max_tokens"), "3000"));
        temperature = Double.parseDouble(Objects.requireNonNullElse(properties.getProperty("temperature"), "0.5"));
        top_p = Double.parseDouble(properties.getProperty("top_p"));
        stream = Boolean.getBoolean(Objects.requireNonNullElse(properties.getProperty("stream"), "false"));
        num = Integer.parseInt(Objects.requireNonNullElse(properties.getProperty("n"), "1"));
        frequency_penalty = Double.parseDouble(Objects.requireNonNullElse(properties.getProperty("frequency_penalty"), "0"));
        presence_penalty = Double.parseDouble(Objects.requireNonNullElse(properties.getProperty("presence_penalty"), "0"));
        service = new OpenAiService(API_KEY, Duration.ofSeconds(30));
    }

    private ChatCompletionRequest getCompletionRequest(String user) {
        return ChatCompletionRequest.builder().model(model)
                .messages(conversation).temperature(temperature)
                .maxTokens(max_tokens).frequencyPenalty(frequency_penalty)
                .presencePenalty(presence_penalty).n(num).stream(stream)
                .topP(top_p).user(user)
                .build();
    }

    private void reduce(int count) {
        if (count <= 2048 >>> 1) {
            return;
        }
        System.out.println("\n开始减少\n");
        for (int i = 0; i < conversation.size() / 2; i++) {
            count -= conversation.remove(0).getContent().length();
        }
    }

    private void send(MessageEvent event) {
        reduce(count);
        ChatMessage userChatMessage = new ChatMessage();
        String string = event.getMessage().contentToString();
        userChatMessage.setContent(string.replaceFirst("p", ""));
        userChatMessage.setRole("system");
        conversation.add(userChatMessage);
        ChatCompletionRequest completionRequest = getCompletionRequest(String.valueOf(event.getSender().getId()));
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();
        System.out.println(choices);
        ChatMessage botChatMessage = choices.get(0).getMessage();
        conversation.add(botChatMessage);
        System.out.println(conversation);
        String content = getChoice(choices);
        count += content.length() + string.length();
        System.out.println(count);
        event.getSubject().sendMessage(content.replaceFirst("\\n\\n", ""));
    }

    private String getChoice(List<ChatCompletionChoice> choices) {
        return choices.get(0).getMessage().getContent();
    }

    @Message(filterRule = FilterRule.BLACK, messageRule = MessageRule.BEGIN_WITH, permission = PermissionType.ALL, text = "p")
    private void test(MessageEvent event) {
        send(event);
    }
}