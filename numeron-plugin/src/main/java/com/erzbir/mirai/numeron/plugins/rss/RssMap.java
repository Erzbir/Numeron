package com.erzbir.mirai.numeron.plugins.rss;

import net.mamoe.mirai.contact.Contact;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2023/3/7 11:44
 */
public class RssMap implements Serializable {
    private HashMap<Contact, RssItem> map = new HashMap<>();

    public HashMap<Contact, RssItem> getMap() {
        return map;
    }
}
