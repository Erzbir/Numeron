package com.erzbir.numeron.event.message;

import com.erzbir.numeron.action.ReplySupport;
import com.erzbir.numeron.event.bot.BotEvent;
import com.erzbir.numeron.message.MessageContent;

/**
 * @author Erzbir
 * @Data: 2024/2/7 23:00
 */
public interface MessageEvent extends BotEvent, ReplySupport {
    MessageContent getContent();
}
