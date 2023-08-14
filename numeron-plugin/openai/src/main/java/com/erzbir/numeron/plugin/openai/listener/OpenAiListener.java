package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.MessageFilter;
import com.erzbir.numeron.enums.FilterRule;
import com.erzbir.numeron.enums.MessageRule;
import com.erzbir.numeron.enums.PermissionType;
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
            help = "/list model",
            permission = PermissionType.ALL
    )
    @Handler
    @MessageFilter(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.EQUAL,
            text = "/list model",
            permission = PermissionType.ALL
    )
    private void listModel(MessageEvent event) {
        String string = OpenAiServiceImpl.INSTANCE.OPENAISERVICE.listModels().stream()
                .map(t -> "id: " + t.id + ", " + "owner: " + t.ownedBy + "\n")
                .collect(Collectors.toCollection(ArrayList::new)).toString();
        event.getSubject().sendMessage(string);

    }
}
