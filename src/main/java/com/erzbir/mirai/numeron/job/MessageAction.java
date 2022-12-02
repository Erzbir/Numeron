package com.erzbir.mirai.numeron.job;

import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:09
 */
@Getter
@Setter
public class MessageAction extends AbstractAction {
    private MessageChain messages;
    private Contact contact;

    public MessageAction(String name, MessageChain messages, Contact contact) {
        super(name);
        this.messages = messages;
        this.contact = contact;
    }

    @Override
    public void run() {
        contact.sendMessage(messages);
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
