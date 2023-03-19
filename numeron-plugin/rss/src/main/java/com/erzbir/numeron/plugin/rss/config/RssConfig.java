package com.erzbir.numeron.plugin.rss.config;

import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.utils.ConfigCreateUtil;
import com.erzbir.numeron.core.utils.ConfigReadException;
import com.erzbir.numeron.core.utils.ConfigWriteException;
import com.erzbir.numeron.core.utils.JsonUtil;
import com.erzbir.numeron.plugin.rss.entity.RssItem;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:02
 */
public class RssConfig implements Serializable {
    private static final String configFile = NumeronBot.INSTANCE.getFolder() + "plugin-configs/rss/config.json";
    private static final Object key = new Object();
    private static volatile RssConfig INSTANCE;

    static {
        try {
            ConfigCreateUtil.createFile(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SerializedName("proxy_type")
    private String proxyType;
    @SerializedName("proxy_address")
    private String proxyAddress;
    @SerializedName("proxy_port")
    private int proxyPort;
    @SerializedName("proxy_username")
    private String proxyUsername;
    @SerializedName("proxy_password")
    private String proxyPassword;
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
        return INSTANCE == null ? new RssConfig() : INSTANCE;
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

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUsername() {
        return proxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        this.proxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
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
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return
                "proxy_type: " + proxyType + '\n' +
                        "proxy_address: " + proxyAddress + '\n' +
                        "proxy_port: " + proxyPort + '\n' +
                        "proxy_username: " + proxyUsername + '\n' +
                        "proxy_password: " + proxyPassword + '\n' +
                        "retry_times: " + retryTimes + '\n' +
                        "delay: " + delay;
    }
}
