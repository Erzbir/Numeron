package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * @author Erzbir
 * @Date: 2022/12/7 21:14
 */
public interface IMessageJob extends IContactJob {
    void send(MessageChain messages, Contact contact);
}
