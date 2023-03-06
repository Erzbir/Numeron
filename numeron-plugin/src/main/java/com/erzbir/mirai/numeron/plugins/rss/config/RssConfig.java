package com.erzbir.mirai.numeron.plugins.rss.config;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.utils.ConfigCreateUtil;
import com.erzbir.mirai.numeron.utils.JsonUtil;
import net.mamoe.mirai.contact.Contact;

import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:02
 */
public class RssConfig {
    private static final String configFile = NumeronBot.INSTANCE.getFolder() + "plugin/rss/config.json";
    private static final Object key = new Object();
    private static volatile RssConfig INSTANCE;

    static {
        ConfigCreateUtil.createFile(configFile);
    }

    protected HashMap<Contact, String> urlList; // 订阅列表
    protected long receiver;    //  接收方
    protected long delay;   //更新间隔
    protected String maxSub; // 最大订阅数量
    protected String proxy_type;    // 代理类型
    protected String proxy_address;    // 代理地址
    protected String proxy_port;    // 代理端口
    protected String proxy_username;    // 代理用户名
    protected String proxy_password;    // 代理密码

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

    public HashMap<Contact, String> getUrlList() {
        return urlList;
    }

    public void setUrlList(HashMap<Contact, String> urlList) {
        this.urlList = urlList;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getMaxSub() {
        return maxSub;
    }

    public void setMaxSub(String maxSub) {
        this.maxSub = maxSub;
    }

    public String getProxy_type() {
        return proxy_type;
    }

    public void setProxy_type(String proxy_type) {
        this.proxy_type = proxy_type;
    }

    public String getProxy_address() {
        return proxy_address;
    }

    public void setProxy_address(String proxy_address) {
        this.proxy_address = proxy_address;
    }

    public String getProxy_port() {
        return proxy_port;
    }

    public void setProxy_port(String proxy_port) {
        this.proxy_port = proxy_port;
    }

    public String getProxy_username() {
        return proxy_username;
    }

    public void setProxy_username(String proxy_username) {
        this.proxy_username = proxy_username;
    }

    public String getProxy_password() {
        return proxy_password;
    }

    public void setProxy_password(String proxy_password) {
        this.proxy_password = proxy_password;
    }
}
