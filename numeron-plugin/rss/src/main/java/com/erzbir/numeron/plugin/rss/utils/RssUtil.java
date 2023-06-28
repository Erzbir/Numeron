package com.erzbir.numeron.plugin.rss.utils;

import com.erzbir.numeron.plugin.rss.entity.RssInfo;
import com.erzbir.numeron.utils.NumeronLogUtil;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2023/3/7 00:45
 */
public class RssUtil {

    public static RssInfo getNewestRssInfo(String url) {
        RssInfo rssInfo = new RssInfo();
        try (XmlReader xmlReader = new XmlReader(new URL(url).openConnection().getInputStream())) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(xmlReader);
            List<SyndEntry> entries = feed.getEntries();
            parseRss(rssInfo, getNewest(entries));
        } catch (FeedException | IOException e) {
            NumeronLogUtil.logger.error(e);
        }
        return rssInfo;
    }

    public static void parseRss(RssInfo rssInfo, SyndEntry entry) {
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
    }

    public static List<RssInfo> getRssInfoList(String url) {
        List<RssInfo> retList = new ArrayList<>();
        try (XmlReader xmlReader = new XmlReader(new URL(url).openConnection().getInputStream())) {
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(xmlReader);
            List<SyndEntry> entries = feed.getEntries();
            for (SyndEntry entry : entries) {
                RssInfo rssInfo = new RssInfo();
                parseRss(rssInfo, entry);
                retList.add(rssInfo);
            }
        } catch (FeedException | IOException e) {
            NumeronLogUtil.logger.error(e);
        }
        return retList;
    }

    private static SyndEntry getNewest(List<SyndEntry> entries) {
        return entries.get(0);
    }
}
