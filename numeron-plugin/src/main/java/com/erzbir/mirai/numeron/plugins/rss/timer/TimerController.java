package com.erzbir.mirai.numeron.plugins.rss.timer;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssItem;
import com.erzbir.mirai.numeron.utils.MiraiContactUtils;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Erzbir
 * @Date: 2023/3/6 00:20
 */
public class TimerController {
    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5, r -> {
        Thread thread = new Thread(r);
        thread.setName("Rss  " + r);
        return thread;
    });

    private static TimerTask getTimerTask(String id) {
        return new TimerTask() {
            @Override
            public void run() {
                RssItem rssItem = RssConfig.getInstance().getRssMap().get(id);
                if (rssItem == null) {
                    return;
                }
                rssItem.getGroupList().forEach(t -> {
                    Group group = MiraiContactUtils.getGroup(t);
                    try {
                        if (rssItem.isEnable()) {
                            group.sendMessage((rssItem.updateInfo().getMessageChain(group)));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                rssItem.getUserList().forEach(t -> {
                    Friend friend = MiraiContactUtils.getFriend(t);
                    try {
                        if (rssItem.isEnable()) {
                            friend.sendMessage((rssItem.updateInfo().getMessageChain(friend)));
                            System.out.println("asadsad");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
    }

    public static void loadAllScan() {
        RssConfig.getInstance().getRssMap().forEach((k, v) -> {
            TimerTask timerTask = getTimerTask(k);
            executorService.schedule(timerTask, RssConfig.getInstance().getDelay(), TimeUnit.MINUTES);
        });
    }

    private static void disableScan() {
        executorService.shutdown();
    }

    public static void addScan(String id, Long delay) {
        TimerTask task = getTimerTask(id);
        executorService.schedule(task, delay, TimeUnit.MINUTES);
    }
}
