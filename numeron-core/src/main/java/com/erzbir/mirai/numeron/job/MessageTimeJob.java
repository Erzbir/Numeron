package com.erzbir.mirai.numeron.job;

import com.erzbir.mirai.numeron.job.inter.IContactJob;
import com.erzbir.mirai.numeron.job.inter.IMessageJob;
import com.erzbir.mirai.numeron.job.inter.TimeTask;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:09
 */
public class MessageTimeJob extends TimeTask implements IMessageJob, IContactJob {
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

    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
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

    public MessageChain getMessages() {
        return messages;
    }

    public void setMessages(MessageChain messages) {
        this.messages = messages;
    }

    @Override
    public void execute() {
        for (Contact contact : contacts) {
            send(messages, contact);
        }
    }
}