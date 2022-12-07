package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/7 21:14
 */
public interface MessageTimeAction extends TimeAction {
    void send(@NotNull MessageChain messages, @NotNull Contact contact);
}
