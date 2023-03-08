package com.erzbir.mirai.numeron.plugins.rss.test;

import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.utils.JsonUtil;

/**
 * @author Erzbir
 * @Date: 2023/3/7 19:35
 */
@Listener
public class RssConfig {
    public static RssConfig INSTANCE;

    public static RssConfig getInstance() {
        return (INSTANCE = JsonUtil.load("erzbirnumeron/plugin-configs/rss/config.json", RssConfig.class));
    }

    public static void main(String[] args) {
        com.erzbir.mirai.numeron.plugins.rss.config.RssConfig.getInstance().getRssMap().forEach((k, v) -> {
            System.out.println(v);
            v.forEach((c, b) -> {

            });
        });
    }


}
