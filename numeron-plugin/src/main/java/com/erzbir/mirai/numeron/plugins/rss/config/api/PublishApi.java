package com.erzbir.mirai.numeron.plugins.rss.config.api;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssItem;
import com.erzbir.mirai.numeron.plugins.rss.timer.TimerController;

/**
 * @author Erzbir
 * @Date: 2023/3/8 12:24
 */
public class PublishApi {
    public static void addPublish(String url) {
        RssConfig instance = RssConfig.getInstance();
        RssItem rssItem = new RssItem(System.currentTimeMillis(), url, true);
        instance.addPublish(rssItem);
        TimerController.addScan(String.valueOf(instance.getRssMap().size() + 1), instance.getDelay());
    }

    public static void deletePublish(String id) {
        RssConfig.getInstance().deletePublish(id);
    }

    public static void disablePublish(String id) {
        RssConfig.getInstance().disablePublish(id);
    }

    public static void enablePublish(String id) {
        RssConfig.getInstance().enablePublish(id);
    }
}
