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
    @SerializedName("name")
    private String name = "";
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

    public RssItem(String name, String url, boolean enable) {
        this.name = name;
        this.url = url;
        this.enable = enable;
    }

    public RssItem(String name, String url, Set<Long> groupList, Set<Long> userList, boolean enable, RssInfo rssInfo) {
        this.name = name;
        this.url = url;
        this.groupList = groupList;
        this.userList = userList;
        this.enable = enable;
        this.rssInfo = rssInfo;
    }

    public RssInfo updateInfo() {
        RssInfo rssInfo1 = RssUtil.getRssInfo(url);
        if (rssInfo.getPublishedDate().getTime() - rssInfo1.getPublishedDate().getTime() >= 0) {
            return null;
        }
        this.rssInfo = rssInfo1;
        return rssInfo1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "name: " + name + '\n' +
                "url: " + url + '\n' +
                "groupList: " + groupList + '\n' +
                "userList: " + userList + '\n' +
                "enable: " + enable + '\n' +
                '\n';
    }
}
