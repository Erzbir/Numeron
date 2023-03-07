package com.erzbir.mirai.numeron.plugins.rss;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.IOException;
import java.util.*;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:20
 */
public class TimerController {
    private static final HashMap<Contact, Timer> timerMap = new HashMap<>();
    private static final HashMap<Contact, Timer> scanMap = new HashMap<>();

    private static TimerTask getTimerTask(MessageEvent event) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    event.getSubject().sendMessage(RssConfig.getInstance().getRssMap().getMap().get(event.getSubject()).getUpdate().getMessageChain(event.getSubject()));
                } catch (IOException e) {
                    event.getSubject().sendMessage("订阅出错");
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    event.getSubject().sendMessage("没有此订阅");
                }
            }
        };
    }

    public static void enableTimingPush(MessageEvent event, Calendar date) {
        if (date.getTime().before(new Date())) {
            date.add(Calendar.DATE, 1);
        }
        TimerTask task = getTimerTask(event);
        Timer timer = new Timer();
        timer.schedule(task, date.getTime(), 24 * 60 * 60 * 1000);
        timerMap.put(event.getSubject(), timer);
    }

    public static void disableTimingPush(Contact contact) {
        Timer timer = timerMap.remove(contact);
        timer.cancel();
    }

    private static void disableScan(Contact contact) {
        Timer timer = scanMap.remove(contact);
        timer.cancel();
    }

    private static void enableScan(MessageEvent event, long delay) {
        TimerTask task = getTimerTask(event);
        Timer timer = new Timer();
        timer.schedule(task, delay);
        scanMap.put(event.getSubject(), timer);
    }

    public static HashMap<Contact, Timer> getTimerMap() {
        return timerMap;
    }

    public static HashMap<Contact, Timer> getScanMap() {
        return scanMap;
    }

}
