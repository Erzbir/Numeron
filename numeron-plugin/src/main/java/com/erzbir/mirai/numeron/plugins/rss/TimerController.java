package com.erzbir.mirai.numeron.plugins.rss;

import com.erzbir.mirai.numeron.plugins.rss.config.Model;
import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import net.mamoe.mirai.contact.Contact;

import java.io.IOException;
import java.util.*;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:20
 */
public class TimerController {
    private static final HashMap<Long, HashMap<Long, Timer>> timerMap = new HashMap<>();
    private static final HashMap<Long, HashMap<Long, Timer>> scanMap = new HashMap<>();

    static {
        RssConfig.getInstance().getRssMap().forEach((k, v) -> v.forEach((m, n) -> {
            HashMap<Long, Timer> hashMap = new HashMap<>();
            hashMap.put(n.getId(), new Timer());
            if (n.getModel().equals(Model.instant)) {
                scanMap.put(k, hashMap);
            } else if (n.getModel().equals(Model.timing)) {
                timerMap.put(k, hashMap);
            }
        }));
    }

    private static TimerTask getTimerTask(Contact contact, long id) {
        return new TimerTask() {
            @Override
            public void run() {
                RssItem rssItem = RssConfig.getInstance().getRssMap().get(contact.getId()).get(12313L);
                try {
                    contact.sendMessage(rssItem.updateInfo().getMessageChain(contact));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static void enableAll(Contact contact) {
        System.out.println(RssConfig.getInstance().getRssMap());
        scanMap.get(contact.getId()).forEach((k, v) -> {
            addScan(contact, k);
        });
    }

    public static void addTimingPush(Contact contact, long id, Calendar date) {
        if (date.getTime().before(new Date())) {
            date.add(Calendar.DATE, 1);
        }
    //    TimerTask task = getTimerTask(contact, id);
        Timer timer = new Timer();
     //   timer.schedule(task, date.getTime(), 24 * 60 * 60 * 1000);
        HashMap<Long, Timer> hashMap = new HashMap<>();
        hashMap.put(id, timer);
        timerMap.put(contact.getId(), hashMap);
    }

    public static void disableTimingPush(long contactId, long id) {
        timerMap.get(contactId).get(id).cancel();
    }

    private static void disableScan(long contactId, long id) {
        scanMap.get(contactId).get(id).cancel();
    }

    private static void disableAll(long contactId) {
        timerMap.get(contactId).forEach((k, v) -> v.cancel());
        scanMap.get(contactId).forEach((k, v) -> v.cancel());
    }

    private static void deleteTiming(long contactId, long id) {
        timerMap.get(contactId).remove(id).cancel();
    }

    private static void deleteScan(long contactId, long id) {
        scanMap.get(contactId).remove(id).cancel();
    }

    private static void deleteAll(long contactId) {
        HashMap<Long, Timer> remove = scanMap.remove(contactId);
        remove.forEach((k, v) -> remove.remove(k).cancel());
        HashMap<Long, Timer> remove2 = timerMap.remove(contactId);
        remove2.forEach((k, v) -> remove.remove(k).cancel());
    }

    public static void addScan(Contact contact, long id) {
        TimerTask task = getTimerTask(contact, id);
        Timer timer = new Timer();
        long delay = RssConfig.getInstance().getDelay();
        timer.schedule(task, delay);
        HashMap<Long, Timer> hashMap = new HashMap<>();
        hashMap.put(id, timer);
        scanMap.put(contact.getId(), hashMap);
    }

    public static HashMap<Long, HashMap<Long, Timer>> getTimerMap() {
        return timerMap;
    }

    public static HashMap<Long, HashMap<Long, Timer>> getScanMap() {
        return scanMap;
    }

}
