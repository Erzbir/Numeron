package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.openai.OpenAiServiceImpl;
import com.erzbir.numeron.plugin.openai.config.CompletionConfig;
import com.theokanning.openai.completion.CompletionRequest;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/19 22:08
 */
@Listener
@Menu(name = "openai")
@SuppressWarnings("unused")
public class CompletionListener {
    private final CompletionConfig COMPLETION_CONFIG = CompletionConfig.getInstance();

    @Command(
            name = "OpenAI",
            dec = "补全",
            help = "/f 水面",
            permission = PermissionType.ALL
    )
    @Handler
    @MessageFilter(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/f\\s+.+",
            permission = PermissionType.ALL
    )
    private void completion(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/f\\s+", "");
        String text = OpenAiServiceImpl.INSTANCE.OPENAISERVICE.createCompletion(createRequest(s)).getChoices().get(0).getText();
        text = text.replaceFirst("\\n\\n", "").replaceFirst("\\?", "").replaceFirst("？", "");
        OpenAiServiceImpl.INSTANCE.sendMessage(event, text);
    }

    private CompletionRequest createRequest(String content) {
        CompletionRequest request = COMPLETION_CONFIG.load();
        request.setPrompt(content);
        return request;
    }
}
