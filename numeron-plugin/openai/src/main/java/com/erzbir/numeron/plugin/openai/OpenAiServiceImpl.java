package com.erzbir.numeron.plugin.openai;

import com.erzbir.numeron.plugin.openai.config.OpenAiConfig;
import com.theokanning.openai.service.OpenAiService;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.time.Duration;

/**
 * @author Erzbir
 * @Date: 2023/6/19 21:42
 */
public class OpenAiServiceImpl {
    public static final OpenAiServiceImpl INSTANCE = new OpenAiServiceImpl();
    public final OpenAiConfig OPENAICONFIG = OpenAiConfig.getInstance();

    public final OpenAiService OPENAISERVICE = new OpenAiService(OPENAICONFIG.getToken(), Duration.ofSeconds(OPENAICONFIG.getTimeout()));

    private OpenAiServiceImpl() {

    }

    public void sendMessage(MessageEvent event, String messages) {
        if (OPENAICONFIG.isChat_by_at()) {
            event.getSubject().sendMessage(new MessageChainBuilder().append(new At(event.getSender().getId())).append(messages).build());
        } else if (OPENAICONFIG.isReply()) {
            event.getSubject().sendMessage(new MessageChainBuilder().append(new QuoteReply(event.getMessage())).append(messages).build());
        } else {
            event.getSubject().sendMessage(new MessageChainBuilder().append(messages).build());
        }
    }

}
