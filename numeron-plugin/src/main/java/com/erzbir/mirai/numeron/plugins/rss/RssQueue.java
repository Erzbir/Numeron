package com.erzbir.mirai.numeron.plugins.rss;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Erzbir
 * @Date: 2023/3/7 11:14
 */
public class RssQueue implements Serializable {
    private Queue<RssItem> rssItems = new LinkedList<>();

    public Queue<RssItem> getRssItems() {
        return rssItems;
    }
}