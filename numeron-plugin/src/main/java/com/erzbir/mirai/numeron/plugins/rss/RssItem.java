package com.erzbir.mirai.numeron.plugins.rss;

import com.erzbir.mirai.numeron.plugins.rss.config.Model;
import com.erzbir.mirai.numeron.plugins.rss.config.ReceiverType;
import com.erzbir.mirai.numeron.plugins.rss.utils.RssUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:31
 */
public class RssItem implements Serializable {
    private long id;
    private String url;
    private Model model = Model.instant;
    private boolean enable = true;
    private ReceiverType receiverType;
    private transient RssInfo rssInfo = new RssInfo();

    public RssItem() {

    }

    public RssItem(long id, String url, Model model, boolean enable, ReceiverType receiverType) {
        this.id = id;
        this.url = url;
        this.model = model;
        this.enable = enable;
        this.receiverType = receiverType;
    }

    public RssInfo updateInfo() {
        return RssUtil.getRssInfo(url);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RssInfo getRssInfo() {
        return rssInfo;
    }

    public void setRssInfo(RssInfo rssInfo) {
        this.rssInfo = rssInfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public ReceiverType getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(ReceiverType receiverType) {
        this.receiverType = receiverType;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "url='" + url + '\'' +
                ", model=" + model +
                ", enable=" + enable +
                ", receiverType=" + receiverType +
                '}';
    }
}
