package com.erzbir.mirai.numeron.plugins.rss;

import com.erzbir.mirai.numeron.plugins.rss.config.Model;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:31
 */
public class RssItem implements Serializable {
    private String url;
    private Model model = Model.INSTANT;
    private RssInfo rssInfo;

    public RssInfo getUpdate() {
        try {
            List<RssInfo> rssList = ParseRss.getRssInfoList(url);
            RssInfo newest = ParseRss.getNewest(rssList);
            return newest;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
