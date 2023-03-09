package com.erzbir.mirai.numeron.plugins.help;

import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.processor.CommandAnnotationProcessor;
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
        event.getSubject().sendMessage(CommandAnnotationProcessor.getHelp());
    }
}