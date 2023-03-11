package com.erzbir.mirai.numeron.plugins.rss.timer;

import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssInfo;
import com.erzbir.mirai.numeron.plugins.rss.entity.RssItem;
import com.erzbir.mirai.numeron.utils.MiraiContactUtils;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;

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
            if (rssItem == null) {
                return;
            }
            RssInfo rssInfo = rssItem.updateInfo();
            if (rssInfo != null) {
                rssItem.getGroupList().forEach(t -> {
                    int flag = RssConfig.getInstance().getRetryTimes();
                    while (flag > 0) {
                        Group group = MiraiContactUtils.getGroup(t);
                        try {
                            if (rssItem.isEnable()) {
                                group.sendMessage((rssInfo.getMessageChain(group)));
                                flag = 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            flag--;
                        }
                    }
                });
                rssItem.getUserList().forEach(t -> {
                    int flag = RssConfig.getInstance().getRetryTimes();
                    while (flag > 0) {
                        Friend friend = MiraiContactUtils.getFriend(t);
                        try {
                            if (rssItem.isEnable()) {
                                friend.sendMessage((rssInfo.getMessageChain(friend)));
                                flag = 0;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            flag--;
                        }
                    }
                });
            }
        };
    }

    public static void loadAllScan() {
        RssConfig.getInstance().getRssMap().forEach((k, v) -> {
            Runnable timerTask = getTimerTask(k);
            executorService.scheduleAtFixedRate(timerTask, 1, RssConfig.getInstance().getDelay(), TimeUnit.MINUTES);
        });
    }

    private static void disableScan() {
        executorService.shutdown();
    }

    public static void addScan(String id, Long delay) {
        Runnable task = getTimerTask(id);
        executorService.scheduleAtFixedRate(task, 1, delay, TimeUnit.MINUTES);
    }
}
