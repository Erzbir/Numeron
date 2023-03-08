package com.erzbir.mirai.numeron.plugins.rss.config;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.plugins.rss.RssItem;
import com.erzbir.mirai.numeron.utils.ConfigCreateUtil;
import com.erzbir.mirai.numeron.utils.JsonUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:02
 */
public class RssConfig implements Serializable {
    private static final String configFile = NumeronBot.INSTANCE.getFolder() + "plugin-configs/rss/config.json";
    private static final Object key = new Object();
    private static volatile RssConfig INSTANCE;

    static {
        ConfigCreateUtil.createFile(configFile);
    }

    private HashMap<Long, HashMap<Long, RssItem>> rss = new HashMap<>(); // 订阅
    private long delay = 3000;   //  更新间隔

    public static RssConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    INSTANCE = JsonUtil.load(configFile, RssConfig.class);
                }
            }
        }
        return INSTANCE == null ? new RssConfig() : INSTANCE;
    }

    public HashMap<Long, HashMap<Long, RssItem>> getRssMap() {
        return rss;
    }

    public void setRssMap(HashMap<Long, HashMap<Long, RssItem>> rss) {
        this.rss = rss;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    @Override
    public String toString() {
        return "RssConfig{" +
                "rss=" + rss +
                ", delay=" + delay +
                '}';
    }
}
