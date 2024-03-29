package com.erzbir.numeron.plugin.rss.entity;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Erzbir
 * @Date: 2023/3/6 13:17
 */
public class RssInfo implements Serializable {
    private String url = "";
    private String title = "";
    private String link = "";
    private String author = "";
    private Date publishedDate = new Date(0);

    /**
     * @param contact 联系人
     * @return 消息链
     */
    public MessageChain getMessageChain(Contact contact) {
        Image image = null;
        try {
            image = Contact.uploadImage(contact, new URL(url).openStream());
        } catch (IOException ignored) {

        }
        MessageChainBuilder chainBuilder = new MessageChainBuilder();
        chainBuilder.append(title).append("\n");
        if (image != null) {
            chainBuilder.append(image);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return chainBuilder.append(link).append("\n")
                .append(author).append("\n")
                .append(sdf.format(new Date(publishedDate.getTime())))
                .build();
    }

    @Override
    public String toString() {
        return "RssInfo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }

    private void clear() {
        this.link = null;
        this.author = null;
        this.title = null;
        this.publishedDate = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
