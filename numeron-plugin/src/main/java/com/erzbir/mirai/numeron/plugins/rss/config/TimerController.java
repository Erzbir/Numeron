package com.erzbir.mirai.numeron.plugins.rss.config;

import net.mamoe.mirai.contact.Contact;

import java.util.*;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:20
 */
public class TimerController {
    HashMap<Contact, Timer> timerMap = new HashMap<>();
    HashMap<Contact, Timer> scanMap = new HashMap<>();

    public void enableTimingPush(Contact contact, Calendar date) {
//        Calendar date = Calendar.getInstance();
//        date.set(Calendar.HOUR_OF_DAY, 8);
//        date.set(Calendar.MINUTE, 0);
//        date.set(Calendar.SECOND, 0);
//        date.set(Calendar.MILLISECOND, 0);
        // Set time to 8:00 AM.
        if (date.getTime().before(new Date())) {
            date.add(Calendar.DATE, 1);
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello World!");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, date.getTime(), 24 * 60 * 60 * 1000);
        timerMap.put(contact, timer);
    }

    public void disableTimingPush(Contact contact) {
        Timer timer = timerMap.remove(contact);
        timer.cancel();
    }

    private void disableScan(Contact contact) {
        Timer timer = scanMap.remove(contact);
        timer.cancel();
    }

    private void enableScan(Contact contact, long delay) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("scan");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, delay);
        scanMap.put(contact, timer);
    }
}
