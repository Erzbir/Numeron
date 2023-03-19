package com.erzbir.numeron.plugin.rss.utils;

import com.erzbir.numeron.plugin.rss.entity.RssInfo;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2023/3/7 00:45
 */
public class RssUtil {
    public static List<RssInfo> getRssInfoList(String url) throws IOException {
        List<RssInfo> retList = new ArrayList<>();
        URL url2 = new URL(url);
        try (XmlReader xmlReader = new XmlReader(url2.openConnection().getInputStream())) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(xmlReader);
            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                RssInfo rssInfo = new RssInfo();
                rssInfo.setTitle(entry.getTitle());
                rssInfo.setLink(entry.getLink());
                SyndContent content = entry.getDescription();
                rssInfo.setPublishedDate(entry.getPublishedDate());
                rssInfo.setAuthor(entry.getAuthor());
                Pattern pattern = Pattern.compile("<img src=\"(.+?)\"");
                Matcher matcher = pattern.matcher(content.getValue());
                if (matcher.find()) {
                    rssInfo.setUrl(matcher.group(1));
                }
                retList.add(rssInfo);
            }
        } catch (FeedException e) {
            throw new RuntimeException(e);
        }
        return retList;
    }

    public static RssInfo getRssInfo(String url) {
        try {
            List<RssInfo> rssList = getRssInfoList(url);
            return getNewest(rssList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static RssInfo getNewest(List<RssInfo> entries) {
        return entries.stream()
                .max(Comparator.comparingLong(o -> o.getPublishedDate().getTime())).orElse(null);
    }
}
