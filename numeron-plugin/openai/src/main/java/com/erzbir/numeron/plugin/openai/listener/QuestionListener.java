package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.openai.OpenAiServiceImpl;
import com.erzbir.numeron.plugin.openai.config.QuestionConfig;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/19 22:11
 */
@Listener
@Menu(name = "openai")
@SuppressWarnings("unused")
public class QuestionListener {
    private final QuestionConfig QUESTION_CONFIG = QuestionConfig.getInstance();

    @Command(
            name = "OpenAI",
            dec = "问答",
            help = "/q 今天天气如何",
            permission = PermissionType.ALL
    )
    @Handler
    @MessageFilter(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/q\\s+.+",
            permission = PermissionType.ALL
    )
    private void question(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/q\\s+", "");
        String text = OpenAiServiceImpl.INSTANCE.OPENAISERVICE.createChatCompletion(createRequest(s)).getChoices().get(0).getMessage().getContent();
        text = text.replaceFirst("\\n\\n", "").replaceFirst("\\?", "").replaceFirst("？", "");
        OpenAiServiceImpl.INSTANCE.sendMessage(event, text);
    }

    private ChatCompletionRequest createRequest(String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setRole("system");
        ChatCompletionRequest request = QUESTION_CONFIG.load();
        List<ChatMessage> list = new ArrayList<>(10);
        list.add(chatMessage);
        request.setMessages(list);
        return request;
    }

}
