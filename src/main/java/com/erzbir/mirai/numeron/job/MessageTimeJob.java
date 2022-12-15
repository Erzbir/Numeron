package com.erzbir.mirai.numeron.job;

import com.erzbir.mirai.numeron.job.inter.IMessageJob;
import com.erzbir.mirai.numeron.job.inter.TimeTask;
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
public class MessageTimeJob extends TimeTask implements IMessageJob {
    private Contact[] contacts;
    private MessageChain messages;

    private MessageTimeJob(String id, String cron) {
        super(id);
        this.cron = cron;
    }

    public MessageTimeJob(String id, String cron, MessageChain messages, Contact... contacts) {
        this(id, cron);
        this.contacts = contacts;
        this.messages = messages;

    }

    @Override
    public void setContact(Contact[] contacts) {
        this.contacts = contacts;
    }

    @Override
    public Contact[] getContacts() {
        return contacts;
    }

    @Override
    public void send(MessageChain messages, Contact contact) {
        contact.sendMessage(messages);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getCron() {
        return cron;
    }

    @Override
    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public void execute() {
        for (Contact contact : contacts) {
            send(messages, contact);
        }
    }
}