package com.erzbir.numeron.plugin.rss.timer;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.plugin.rss.config.RssConfig;
import com.erzbir.numeron.plugin.rss.entity.RssInfo;
import com.erzbir.numeron.plugin.rss.entity.RssItem;
import com.erzbir.numeron.utils.MiraiContactUtils;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.Bot;
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
            if (rssItem == null) {
                return;
            }
            RssInfo rssInfo = rssItem.updateInfo();
            send(rssInfo, rssItem.getUserList(), Friend.class);
            send(rssInfo, rssItem.getGroupList(), Group.class);
        };
    }

    private static void send(RssInfo rssInfo, Set<Long> list, Class<? extends Contact> type) {
        if (rssInfo == null) {
            return;
        }
        list.forEach(t -> {
            for (Bot bot : BotServiceImpl.INSTANCE.getBotList()) {
                int flag = RssConfig.getInstance().getRetryTimes();
                while (flag > 0) {
                    long id = bot.getId();
                    Contact contact = MiraiContactUtils.getContact(id, t, type);
                    try {
                        if (BotServiceImpl.INSTANCE.isEnable(bot) && contact != null) {
                            contact.sendMessage((rssInfo.getMessageChain(contact)));
                        }
                        flag = 0;
                    } catch (Exception e) {
                        NumeronLogUtil.logger.error("ERROR", e);
                        flag--;
                    }
                }
            }
        });
    }

    public static void loadAllScan() {
        RssConfig.getInstance().getRssMap().forEach((k, v) -> {
            Runnable timerTask = getTimerTask(k);
            try {
                executorService.scheduleAtFixedRate(timerTask, 1, RssConfig.getInstance().getDelay(), TimeUnit.MINUTES);
            } catch (Exception e) {
                NumeronLogUtil.logger.error("ERROR", e);
            }
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
