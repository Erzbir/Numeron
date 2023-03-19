package com.erzbir.numeron.plugin.rss.api;

import com.erzbir.numeron.plugin.rss.config.RssConfig;
import com.erzbir.numeron.plugin.rss.entity.RssItem;
import com.erzbir.numeron.plugin.rss.timer.TimerController;

/**
 * @author Erzbir
 * @Date: 2023/3/8 12:24
 */
public class PublishApi {
    public static void addPublish(String url) {
        RssConfig instance = RssConfig.getInstance();
        RssItem rssItem = new RssItem(String.valueOf(System.currentTimeMillis()), url, true);
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

    public static void disableScan() {
        TimerController.disableScan();
    }
}
