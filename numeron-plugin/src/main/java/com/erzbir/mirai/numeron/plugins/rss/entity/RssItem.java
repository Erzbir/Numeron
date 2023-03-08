package com.erzbir.mirai.numeron.plugins.rss.entity;

import com.erzbir.mirai.numeron.plugins.rss.utils.RssUtil;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:31
 */
public class RssItem implements Serializable {
    @SerializedName("id")
    private long id = 0;
    @SerializedName("url")
    private String url;
    @SerializedName("groups")
    private Set<Long> groupList = new HashSet<>();
    @SerializedName("users")
    private Set<Long> userList = new HashSet<>();
    @SerializedName("enable")
    private boolean enable = true;
    private transient RssInfo rssInfo = new RssInfo();

    public RssItem() {

    }

    public RssItem(long id, String url, boolean enable) {
        this.id = id;
        this.url = url;
        this.enable = enable;
    }

    public RssItem(long id, String url, Set<Long> groupList, Set<Long> userList, boolean enable, RssInfo rssInfo) {
        this.id = id;
        this.url = url;
        this.groupList = groupList;
        this.userList = userList;
        this.enable = enable;
        this.rssInfo = rssInfo;
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<Long> getGroupList() {
        return groupList;
    }

    public void setGroupList(Set<Long> groupList) {
        this.groupList = groupList;
    }

    public Set<Long> getUserList() {
        return userList;
    }

    public void setUserList(Set<Long> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "id: " + id + '\n' +
                "url: " + url + '\n' +
                "groupList: " + groupList + '\n' +
                "userList: " + userList + '\n' +
                "enable: " + enable + '\n' +
                '\n';
    }
}
