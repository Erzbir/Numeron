package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.openai.OpenAiServiceImpl;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Erzbir
 * @Date: 2023/3/4 11:36
 */
@Listener
@Menu(name = "openai")
@SuppressWarnings("unused")
public class OpenAiListener {
    @Command(
            name = "OpenAI",
            dec = "获取模型列表",
            help = "/list model"
    )
    @Handler
    @Filter("/list model")
    private void listModel(MessageEvent event) {
        String string = OpenAiServiceImpl.INSTANCE.OPENAISERVICE.listModels().stream()
                .map(t -> "id: " + t.id + ", " + "owner: " + t.ownedBy + "\n")
                .collect(Collectors.toCollection(ArrayList::new)).toString();
        event.getSubject().sendMessage(string);

    }
}
