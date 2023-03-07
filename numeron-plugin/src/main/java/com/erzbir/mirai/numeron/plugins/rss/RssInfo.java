package com.erzbir.mirai.numeron.plugins.rss;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;

/**
 * @author Erzbir
 * @Date: 2023/3/6 13:17
 */
public class RssInfo implements Serializable {
    private String url;
    private String title;
    private String link;
    private String description;
    private String author;
    private Date publishedDate;

    public MessageChain getMessageChain(Contact contact) throws IOException {
        return new MessageChainBuilder()
                .append(title).append("\n")
                .append("--").append(description).append("\n")
                .append(Contact.uploadImage(contact, new URL(url).openStream())).append("\n")
                .append(author).append("\n")
                .append(publishedDate.toString())
                .build();
    }

    @Override
    public String toString() {
        return "RssInfo{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
