package com.erzbir.mirai.numeron.plugins.rss.config;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.plugins.rss.RssMap;
import com.erzbir.mirai.numeron.utils.ConfigCreateUtil;
import com.erzbir.mirai.numeron.utils.JsonUtil;

import java.io.Serializable;

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

    protected RssMap rssMap; // 订阅
    protected long delay;   //更新间隔

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

    public RssMap getRssMap() {
        return rssMap;
    }

    public void setRssMap(RssMap rssMap) {
        this.rssMap = rssMap;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
