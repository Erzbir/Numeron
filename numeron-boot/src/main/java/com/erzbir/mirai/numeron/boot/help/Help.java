package com.erzbir.mirai.numeron.boot.help;

import com.erzbir.mirai.numeron.boot.processor.CommandAnnotationProcessor;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/12/1 21:27
 */
@Listener
@SuppressWarnings("unused")
public class Help {
    @Message(text = "/help", permission = PermissionType.ALL)
    private void help(MessageEvent event) {
        event.getSubject().sendMessage(CommandAnnotationProcessor.stringBuilder.toString());
    }
}