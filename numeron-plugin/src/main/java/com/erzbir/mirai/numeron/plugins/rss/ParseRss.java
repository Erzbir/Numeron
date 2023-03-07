package com.erzbir.mirai.numeron.plugins.rss;

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/3/7 00:45
 */
public class ParseRss {
    public static List<RssInfo> getRssInfoList(String rss) throws IOException {
        List<RssInfo> retList = new ArrayList<>();
        URL url = new URL(rss);
        try (XmlReader xmlReader = new XmlReader(url.openConnection().getInputStream())) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(xmlReader);
            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                RssInfo rssInfo = new RssInfo();
                rssInfo.setTitle(entry.getTitle());
                rssInfo.setLink(entry.getLink());
                SyndContent content = entry.getDescription();
                rssInfo.setDescription(content.getValue());
                rssInfo.setPublishedDate(entry.getPublishedDate());
                rssInfo.setAuthor(entry.getAuthor());
                rssInfo.setUrl(entry.getUri());
                retList.add(rssInfo);
            }
        } catch (FeedException e) {
            throw new RuntimeException(e);
        }
        return retList;
    }

    public static RssInfo getNewest(List<RssInfo> entries) {
        return entries.stream()
                .max((o1, o2) ->
                        (int) (o1.getPublishedDate().getTime() - o2.getPublishedDate().getTime())).orElse(null);
    }
}
