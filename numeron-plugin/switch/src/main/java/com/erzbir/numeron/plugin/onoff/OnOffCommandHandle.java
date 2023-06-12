package com.erzbir.numeron.plugin.onoff;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.entity.WhiteServiceImpl;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
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
    @Message(
            text = "/shutdown//s+//d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.WHITE
    )
    private void shutdown(MessageEvent e) {
        String s = e.getMessage().contentToString().replaceFirst("/shutdown//s+", "");
        long id = e.getBot().getId();
        Long aLong = Long.getLong(s);
        if (id != aLong) {
            return;
        }
        if (BotServiceImpl.INSTANCE.getConfiguration(e.getBot().getId()).isEnable()) {
            e.getSubject().sendMessage("关机");
            BotServiceImpl.INSTANCE.shutdown(aLong);
        }
        if (BotServiceImpl.INSTANCE.getConfiguration(e.getBot().getId()).isEnable()) {
            e.getBot().getEventChannel().subscribe(MessageEvent.class, event -> {
                if ((WhiteServiceImpl.INSTANCE.exist(event.getSender().getId()) || event.getSender().getId() == BotServiceImpl.INSTANCE.getConfiguration(e.getBot().getId()).getMaster()) && event.getMessage().contentToString().equals("/launch")) {
                    e.getSubject().sendMessage("开机");
                    BotServiceImpl.INSTANCE.launch(aLong);
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
