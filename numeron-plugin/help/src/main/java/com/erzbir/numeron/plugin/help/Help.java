package com.erzbir.numeron.plugin.help;

import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.MessageFilter;
import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.enums.PermissionType;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Erzbir
 * @Date: 2022/12/1 21:27
 */
@Listener
@SuppressWarnings("unused")
public class Help {
    @NotNull
    private static String[] getStrings() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(NumeronImpl.INSTANCE.getWorkDir() + "help.txt"))) {
            int len;
            char[] buff = new char[30];
            while ((len = reader.read(buff)) != -1) {
                sb.append(new String(buff, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String string = sb.toString();
        return string.split("---");
    }

    @Handler
    @MessageFilter(
            text = "/help",
            permission = PermissionType.ALL
    )
    private void help(MessageEvent event) {
        String[] split = getStrings();
        ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getSubject());
        Bot bot = event.getBot();
        String senderName = "Numeron";
        builder.add(bot.getId(), senderName, new PlainText("帮助文档"));
        for (String s : split) {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.append(s);
            builder.add(bot.getId(), senderName, messageChainBuilder.build());
        }
        event.getSubject().sendMessage(builder.build());
    }
}