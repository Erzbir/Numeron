package com.erzbir.mirai.numeron.plugins.rss.listner;

import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.rss.RssItem;
import com.erzbir.mirai.numeron.plugins.rss.TimerController;
import com.erzbir.mirai.numeron.plugins.rss.config.Model;
import com.erzbir.mirai.numeron.plugins.rss.config.ReceiverType;
import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import com.erzbir.mirai.numeron.plugins.rss.utils.RssUtil;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:23
 */
@Listener
public class RssListener {
    private final RssConfig rssConfig = RssConfig.getInstance();

    @Command(name = "订阅RSS推送", dec = "#sub [url] [date]", help = "#sub https://xxx.xxx")
    @Message(text = "^#sub_instant\\s*?(http[s]*://.*)\\s+?[0-12]+?:[0-60]+", messageRule = MessageRule.REGEX, filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    private void sub(MessageEvent event) {
        String[] split = event.getMessage().contentToString().replaceFirst("#sub_instant\\s+?", "").split("\\s+?");
        String url = split[0];
        String date = split[1];
        String[] split1 = date.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split1[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(split1[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        RssItem rssItem = new RssItem(System.currentTimeMillis(), url, Model.instant, true, ReceiverType.valueOf(event.getSubject().getClass().getSimpleName().toLowerCase()));
        long l = System.currentTimeMillis();
        HashMap<Long, RssItem> orDefault = RssConfig.getInstance().getRssMap().getOrDefault(l, new HashMap<>());
        orDefault.put(l, rssItem);
        RssConfig.getInstance().getRssMap().put(event.getSubject().getId(), orDefault);
        TimerController.addTimingPush(event.getSubject(), System.currentTimeMillis(), calendar);
    }

    @Command(name = "取消订阅", dec = "#nosub <id>", help = "#nosub 1)")
    @Message(text = "^nosub_instant\\s*?\\d+?", messageRule = MessageRule.REGEX, filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    private void cancel(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("#nosub\\s+?", "");
    }

    @Command(name = "查看本群/好友订阅列表", dec = "#list all", help = "#list all")
    @Message(text = "#list all", filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    private void list(MessageEvent event) {
        TimerController.enableAll(event.getSubject());
    }
}
