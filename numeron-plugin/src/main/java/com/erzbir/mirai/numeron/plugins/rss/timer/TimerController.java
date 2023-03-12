package com.erzbir.mirai.numeron.plugins.rss.timer;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssInfo;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssItem;
import com.erzbir.mirai.numeron.utils.MiraiContactUtils;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:20
 */
public class TimerController {
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    private static Runnable getTimerTask(String id) {
        return () -> {
            RssItem rssItem = RssConfig.getInstance().getRssMap().get(id);
            if (rssItem == null || !rssItem.isEnable()) {
                return;
            }
            RssInfo rssInfo = rssItem.updateInfo();
            if (rssInfo != null) {
                send(rssInfo, rssItem.getUserList(), Friend.class);
                send(rssInfo, rssItem.getGroupList(), Group.class);
            }
        };
    }

    private static void send(RssInfo rssInfo, Set<Long> list, Class<? extends Contact> type) {
        list.forEach(t -> {
            int flag = RssConfig.getInstance().getRetryTimes();
            while (flag > 0) {
                Contact contact = MiraiContactUtils.getContact(t, type);
                if (contact == null) {
                    return;
                }
                try {
                    contact.sendMessage((rssInfo.getMessageChain(contact)));
                    flag = 0;
                } catch (Exception e) {
                    e.printStackTrace();
                    flag--;
                }
            }
        });
    }

    public static void loadAllScan() {
        RssConfig.getInstance().getRssMap().forEach((k, v) -> {
            Runnable timerTask = getTimerTask(k);
            executorService.scheduleAtFixedRate(timerTask, 1, RssConfig.getInstance().getDelay(), TimeUnit.MINUTES);
        });
    }

    public static void disableScan() {
        executorService.shutdown();
    }

    public static void addScan(String id, Long delay) {
        Runnable task = getTimerTask(id);
        executorService.scheduleAtFixedRate(task, 1, delay, TimeUnit.MINUTES);
    }
}
