package com.erzbir.mirai.numeron.job;

import com.erzbir.mirai.numeron.job.inter.ContactTimeAction;
import com.erzbir.mirai.numeron.job.inter.MessageTimeAction;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/2 12:09
 */
@Getter
@Setter
public class AllMessageAction implements ContactTimeAction, MessageTimeAction {
    private static String id = AllMessageAction.class.getName();
    private Contact[] contacts;
    private MessageChain messages;

    public AllMessageAction(MessageChain messages, Contact... contacts) {
        setContact(contacts);
        setMessages(messages);
    }

    public AllMessageAction() {
        this(null, (Contact[]) null);
    }

    public AllMessageAction(Contact[] contacts) {
        this(null, contacts);
    }

    public AllMessageAction(MessageChain messages) {
        this(messages, (Contact[]) null);
    }

    @Override
    public void execute() {
        for (Contact contact : contacts) {
            send(messages, contact);
        }
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
    public void send(@NotNull MessageChain messages, @NotNull Contact contact) {
        contact.sendMessage(messages);
    }
}