package com.erzbir.numeron.action;

import com.erzbir.numeron.message.Message;
import com.erzbir.numeron.message.MessageReceipt;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:06
 */
public interface ReplySupport {
    MessageReceipt reply(String text);

    MessageReceipt reply(Message message);
}
