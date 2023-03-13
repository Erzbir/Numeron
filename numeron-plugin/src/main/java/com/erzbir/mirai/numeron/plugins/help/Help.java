package com.erzbir.mirai.numeron.plugins.help;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.processor.CommandAnnotationProcessor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/1 21:27
 */
@Listener
@SuppressWarnings("unused")
public class Help {
    @Message(
            text = "/help",
            permission = PermissionType.ALL
    )
    private void help(MessageEvent event) {
        HashMap<String, Set<String>> helpMap = CommandAnnotationProcessor.getHelpMap();
        ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getSubject());
        Bot bot = NumeronBot.INSTANCE.getBot();
        String senderName = "Numeron";
        builder.add(bot.getId(), senderName, new PlainText("帮助文档"));
        helpMap.forEach((k, v) -> {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.append(k).append(":\n");
            v.forEach(t -> messageChainBuilder.append(t).append("\n"));
            builder.add(bot.getId(), senderName, messageChainBuilder.build());
        });
        event.getSubject().sendMessage(builder.build());
    }
}