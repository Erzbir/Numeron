package com.erzbir.mirai.numeron.plugins.rss.config;

import com.erzbir.mirai.numeron.plugins.rss.Model;
import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:02
 */
public class RssConfig {
    protected List<String> urlList; // 订阅列表
    protected long receiver;    //  接收方
    protected Model model;  // 推送模式
    protected long delay;   //更新间隔
    protected String maxSub; // 最大订阅数量
    protected String proxy_type;    // 代理类型
    protected String proxy_address;	// 代理地址
    protected String proxy_port;    // 代理端口
    protected String proxy_username;    // 代理用户名
    protected String proxy_password;    // 代理密码

    public RssConfig(List<String> urlList, long receiver, Model model, long delay) {
        this.urlList = urlList;
        this.receiver = receiver;
        this.model = model;
        this.delay = delay;
    }

    public RssConfig(List<String> urlList, long receiver, Model model, long delay, String maxSub, String proxy_type, String proxy_address, String proxy_port, String proxy_username, String proxy_password) {
        this.urlList = urlList;
        this.receiver = receiver;
        this.model = model;
        this.delay = delay;
        this.maxSub = maxSub;
        this.proxy_type = proxy_type;
        this.proxy_address = proxy_address;
        this.proxy_port = proxy_port;
        this.proxy_username = proxy_username;
        this.proxy_password = proxy_password;
    }

    public static RssConfig load() {
        File file = new File(NumeronBot.INSTANCE.getWorkDir() + "plugin/rss/config.json");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Gson gson = new Gson();
        try {
            return gson.fromJson(new BufferedReader(new FileReader(file)), RssConfig.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public long getReceiver() {
        return receiver;
    }

    public void setReceiver(long receiver) {
        this.receiver = receiver;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
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
