package com.erzbir.numeron.plugin.rss.config;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.plugin.rss.entity.RssItem;
import com.erzbir.numeron.utils.*;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:02
 */
public class RssConfig implements Serializable {
    private static final String configFile = NumeronImpl.INSTANCE.getPluginWorkDir() + "/rss/config/config.json";
    private static final Object key = new Object();
    private static volatile RssConfig INSTANCE;

    static {
        try {
            ConfigCreateUtil.createFile(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SerializedName("retry_times")
    private int retryTimes = 5;
    @SerializedName("rss")
    private HashMap<String, RssItem> rss = new HashMap<>(); // 订阅
    @SerializedName("delay")
    private int delay = 1;   //  更新间隔

    public static RssConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (key) {
                if (INSTANCE == null) {
                    try {
                        INSTANCE = JsonUtil.load(configFile, RssConfig.class);
                    } catch (ConfigReadException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        if (INSTANCE == null) {
            INSTANCE = new RssConfig();
            try {
                JsonUtil.dump(configFile, INSTANCE, RssConfig.class);
            } catch (ConfigWriteException e) {
                throw new RuntimeException(e);
            }
        }
        return INSTANCE;
    }

    public void addPublish(RssItem rssItem) {
        rss.put(String.valueOf(rss.size() + 1), rssItem);
    }

    public void deletePublish(String id) {
        rss.remove(id);
    }

    public void enablePublish(String id) {
        rss.get(id).setEnable(true);
    }

    public void disablePublish(String id) {
        rss.get(id).setEnable(false);
    }

    public HashMap<String, RssItem> getRssMap() {
        return rss;
    }

    public void setRssMap(HashMap<String, RssItem> rss) {
        this.rss = rss;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public HashMap<String, RssItem> getRss() {
        return rss;
    }

    public void setRss(HashMap<String, RssItem> rss) {
        this.rss = rss;
    }

    public boolean save() {
        try {
            JsonUtil.dump(configFile, this, this.getClass());
            return true;
        } catch (ConfigWriteException e) {
            NumeronLogUtil.logger.error(e);
        }
        return false;
    }

    @Override
    public String toString() {
        return "retry_times: " + retryTimes + '\n' + "delay: " + delay;
    }
}
