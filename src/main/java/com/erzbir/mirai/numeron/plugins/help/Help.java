package com.erzbir.mirai.numeron.plugins.help;

import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.processor.CommandAnnotationProcessor;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/12/1 21:27
 */
@Listener
public class Help {
    @Message(text = "/help", permission = PermissionType.ALL)
    public void help(MessageEvent event) {
        event.getSubject().sendMessage(CommandAnnotationProcessor.stringBuilder.toString());
    }
}