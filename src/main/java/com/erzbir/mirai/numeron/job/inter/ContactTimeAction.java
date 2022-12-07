package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Contact;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:19
 */
public interface ContactTimeAction extends TimeAction {
    void setContact(@NotNull Contact[] contacts);

    Contact[] getContacts();
}
