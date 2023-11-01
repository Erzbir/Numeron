package com.erzbir.numeron.plugin.onoff;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.PermissionManager;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.api.filter.enums.MatchType;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 09:26
 * <p>
 * 开关机命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class OnOffCommandHandle {

    @Command(
            name = "开关机",
            dec = "关机",
            help = "/shutdown [qq]",
            permission = PermissionType.WHITE
    )
    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter(value = "/shutdown\\s+\\d+", matchType = MatchType.REGEX_MATCHES)
    private void shutdown(MessageEvent e) {
        String s = e.getMessage().contentToString().replaceFirst("/shutdown\\s+", "");
        long id = e.getBot().getId();
        long aLong = Long.parseLong(s);
        if (id != aLong) {
            return;
        }
        if (BotServiceImpl.INSTANCE.getConfiguration(e.getBot().getId()).isEnable()) {
            e.getSubject().sendMessage("关机");
            BotServiceImpl.INSTANCE.shutdown(aLong);
        }
        if (!BotServiceImpl.INSTANCE.getConfiguration(e.getBot().getId()).isEnable()) {
            e.getBot().getEventChannel().subscribe(MessageEvent.class, event -> {
                String s1 = event.getMessage().contentToString();
                if ((PermissionManager.INSTANCE.getContactsOfPermission(ContactType.USER, PermissionType.WHITE).contains(event.getSender().getId()) || event.getSender().getId() == BotServiceImpl.INSTANCE.getConfiguration(e.getBot().getId()).getMaster()) && s1.startsWith("/launch")) {
                    String s2 = s1.replaceFirst("/launch\\s+", "");
                    e.getSubject().sendMessage("开机");
                    BotServiceImpl.INSTANCE.launch(Long.parseLong(s2));
                    return ListeningStatus.STOPPED;
                } else {
                    return ListeningStatus.LISTENING;
                }
            });
        }
    }

    @Command(
            name = "开关机",
            dec = "开机",
            help = "/launch",
            permission = PermissionType.MASTER
    )
    public void launch() {
    }
}
