package com.erzbir.mirai.numeron.job.inter;

import net.mamoe.mirai.contact.Contact;

/**
 * @author Erzbir
 * @Date: 2022/12/5 18:19
 */
public interface IContactJob {
    void setContact(Contact[] contacts);

    Contact[] getContacts();
}
