package com.erzbir.mirai.numeron.plugins.rss.listner;

import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.plugins.rss.TimerController;
import com.erzbir.mirai.numeron.plugins.rss.config.RssConfig;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Calendar;

/**
 * @author Erzbir
 * @Date: 2023/3/7 01:23
 */
@Listener
public class RssMessageListener {
    private final RssConfig rssConfig = RssConfig.getInstance();


    @Command(name = "订阅RSS推送", dec = "#sub [url] [date]", help = "#sub https://xxx.xxx")
    @Message(text = "^#sub\\s*?(http[s]*://.*)\\s+?[0-12]+?:[0-60]+", messageRule = MessageRule.REGEX, filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    private void sub(MessageEvent event) {
        String[] split = event.getMessage().contentToString().replaceFirst("#sub\\s+?", "").split("\\s+?");
        String s = split[0];
        String s1 = split[1];
        String[] split1 = s1.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(split1[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(split1[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        TimerController.enableTimingPush(event, calendar);
    }

    @Command(name = "取消订阅", dec = "#nosub [id]", help = "#nosub 1")
    @Message(text = "^nosub\\s*?\\d+?", messageRule = MessageRule.REGEX, filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    private void cancel(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("#nosub\\s+?", "");
    }

    @Command(name = "查看本联系人订阅列表", dec = "#list", help = "#list")
    @Message(text = "#list", filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    private void list(MessageEvent event) {

    }
}
