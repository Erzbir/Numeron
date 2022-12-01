package com.erzbir.mirai.numeron.plugins.help;

import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

import static com.erzbir.mirai.numeron.processor.CommandHelpProcessor.stringBuilder;

/**
 * @author Erzbir
 * @Date: 2022/12/1 21:27
 */
@Listener
public class Help {
    @Message(text = "/help", permission = PermissionType.WHITE)
    public void help(MessageEvent event) {
        event.getSubject().sendMessage(stringBuilder.toString());
    }
}