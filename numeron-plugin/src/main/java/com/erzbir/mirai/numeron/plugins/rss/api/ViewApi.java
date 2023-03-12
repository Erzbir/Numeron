package com.erzbir.mirai.numeron.plugins.rss.api;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssItem;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;

/**
 * @author Erzbir
 * @Date: 2023/3/8 14:04
 */
public class ViewApi {
    public static String viewAllConfig() {
        return RssConfig.getInstance().toString();
    }

    public static String viewAllRss() {
        StringBuilder sb = new StringBuilder();
        RssConfig.getInstance().getRssMap().forEach((k, v) ->
                sb.append("id: ").append(k).append("\n")
                        .append(v.toString()).append("\n"));
        return sb.toString();
    }

    public static String viewRss(String id) {
        return RssConfig.getInstance().getRssMap().get(id).toString();
    }

    public static String viewReceiveList(String id) {
        RssItem rssItem = RssConfig.getInstance().getRssMap().get(id);
        return "用户: " + rssItem.getUserList().toString() + "\n" +
                "群: " + rssItem.getGroupList();
    }

    public static String viewAllReceiveList() {
        StringBuilder sb = new StringBuilder();
        RssConfig.getInstance().getRssMap()
                .forEach((k, v) ->
                        sb.append("id: ").append(k).append("\n")
                                .append(viewReceiveList(k)).append("\n"));
        return sb.toString();
    }
}
