package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.enums.MatchType;
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
            help = "/f 水面"
    )
    @Handler
    @Filter(value = "^/f\\s+.+", matchType = MatchType.REGEX_MATCHES)
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
