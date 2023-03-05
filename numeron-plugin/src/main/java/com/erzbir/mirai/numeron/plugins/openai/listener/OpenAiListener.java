package com.erzbir.mirai.numeron.plugins.openai.listener;

import com.erzbir.mirai.numeron.plugins.openai.Conversation;
import com.erzbir.mirai.numeron.plugins.openai.ParseImage;
import com.erzbir.mirai.numeron.plugins.openai.config.*;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import com.theokanning.openai.service.OpenAiService;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.time.Duration;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/4 11:36
 */
@Listener
public class OpenAiListener {
    private final Conversation conversation = new Conversation(2048);
    private final Conversation conversation2 = new Conversation(2048);
    private final OpenAiConfig openAiConfig = OpenAiConfig.getInstance();
    private final ImageConfig imageConfig = ImageConfig.getInstance();
    private final ChatConfig chatConfig = ChatConfig.getInstance();
    private final CompletionConfig completionConfig = CompletionConfig.getInstance();
    private final QuestionConfig questionConfig = QuestionConfig.getInstance();
    private final OpenAiService openAiService = new OpenAiService(openAiConfig.getToken(), Duration.ofSeconds(openAiConfig.getTimeout()));
    private final CreateImageRequest imageRequest = imageConfig.load();
    private final CompletionRequest completionRequest = completionConfig.load();
    private final ChatCompletionRequest chatCompletionRequest = chatConfig.load();
    private final CompletionRequest question = questionConfig.load();

    @Command(name = "OpenAI-画图", dec = "i [prompt]", help = "i美女")
    @Message(filterRule = FilterRule.BLACK, messageRule = MessageRule.REGEX, text = "^i\\s*?\\S+?", permission = PermissionType.ALL)
    private void image(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^i\\s*?", "");
        createRequest(s, ImageConfig.class);
        MessageChain singleMessages = buildImageMessage(openAiService.createImage(imageRequest).getData(), event);
        event.getSubject().sendMessage(singleMessages);
    }

    @Command(name = "OpenAI-聊天", dec = "c [message]", help = "c你叫什么")
    @Message(filterRule = FilterRule.BLACK, messageRule = MessageRule.REGEX, text = "^c\\s*?\\S+?", permission = PermissionType.ALL)
    private void chat(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("c\\s*?", "");
        createRequest(s, ChatConfig.class);
        ChatMessage message = openAiService.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
        sendMessage(event, message.getContent().replaceFirst("\\n\\n", "").replaceFirst("\\?", "").replaceFirst("？", ""));
        conversation.add(message);
    }

    @Command(name = "OpenAI-补全", dec = "f [prompt]", help = "f水面")
    @Message(filterRule = FilterRule.BLACK, messageRule = MessageRule.REGEX, text = "^f\\s*?\\S+?", permission = PermissionType.ALL)
    private void completion(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^f\\s*?", "");
        createRequest(s, CompletionConfig.class);
        String text = openAiService.createCompletion(completionRequest).getChoices().get(0).getText();
        text = text.replaceFirst("\\n\\n", "").replaceFirst("\\?", "").replaceFirst("？", "");
        sendMessage(event, text);
    }

    @Command(name = "OpenAI-问答", dec = "q [prompt]", help = "q今天天气如何")
    @Message(filterRule = FilterRule.BLACK, messageRule = MessageRule.REGEX, text = "^q\\s*?\\S+?", permission = PermissionType.ALL)
    private void question(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^q\\s*?", "");
        createRequest(s, QuestionConfig.class);
        String text = openAiService.createCompletion(question).getChoices().get(0).getText();
        text = text.replaceFirst("\\n\\n", "").replaceFirst("\\?", "").replaceFirst("？", "");
        sendMessage(event, text);
    }

    private MessageChain buildImageMessage(List<Image> images, MessageEvent event) {
        MessageChainBuilder chainBuilder = new MessageChainBuilder();
        for (Image image : images) {
            net.mamoe.mirai.message.data.Image image1 = Contact.uploadImage(event.getSubject(), ParseImage.store(image, imageConfig.getFolder(), imageConfig.isSave()));
            if (openAiConfig.isChat_by_at()) {
                chainBuilder.append(new At(event.getSender().getId()).plus(image1));
            } else if (openAiConfig.isReply()) {
                chainBuilder.append(new QuoteReply(event.getMessage()).plus(image1)).build();
            } else {
                chainBuilder.append(image1).build();
            }
        }
        return chainBuilder.build();
    }

    private void sendMessage(MessageEvent event, String messages) {
        if (openAiConfig.isChat_by_at()) {
            event.getSubject().sendMessage(new MessageChainBuilder().append(new At(event.getSender().getId())).append(messages).build());
        } else if (openAiConfig.isReply()) {
            event.getSubject().sendMessage(new MessageChainBuilder().append(new QuoteReply(event.getMessage())).append(messages).build());
        } else {
            event.getSubject().sendMessage(new MessageChainBuilder().append(messages).build());

        }
    }

    private void createRequest(String content, Class<?> type) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setRole("system");
        if (type.equals(ChatConfig.class)) {
            conversation.add(chatMessage);
            chatCompletionRequest.setMessages(conversation);
        } else if (type.equals(CompletionConfig.class)) {
            completionRequest.setPrompt("text-davinci-003");
            completionRequest.setPrompt(content);
        } else if (type.equals(ImageConfig.class)) {
            imageRequest.setPrompt(content);
        } else if (type.equals(QuestionConfig.class)) {
            question.setModel("text-davinci-003");
            question.setPrompt(content);
        }
    }
}
