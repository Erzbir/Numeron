package com.erzbir.mirai.numeron.bot.qqmanage.command;


import com.erzbir.mirai.numeron.entity.GroupList;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:46
 */
@Listener
@SuppressWarnings("unused")
public class MuteCommand {

    @Command(name = "禁言操作", dec = "禁言一个人", help = "/mute [@user] [time] 或者 /mute [qq] [time]")
    @Message(text = "/mute\\s+?@?\\d+?\\s+?\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void muteSingle(MessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+");
        long id;
        int time;
        s[1] = s[1].replaceAll("@", "");
        id = Long.parseLong(s[1]);
        time = Integer.parseInt(s[2]);
        if (event instanceof GroupMessageEvent event1) {
            Objects.requireNonNull(event1.getGroup().get(id)).mute(time);
        } else {
            AtomicReference<NormalMember> member = new AtomicReference<>();
            GroupList.INSTANCE.getGroup().forEach(v -> member.set(Objects.requireNonNull(event.getBot().getGroup(v)).get(id)));
            if (member.get().getPermission().getLevel() < 1) {
                member.get().mute(time);
            }
        }

    }

    @Command(name = "禁言操作", dec = "解禁一个人", help = "/unmute [@user] [time] 或者 /unmute [qq] [time]")
    @Message(text = "/unmute\\s+?@?\\d+?", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void unmuteSingle(MessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+");
        long id;
        int time;
        s[1] = s[1].replaceAll("@", "");
        id = Long.parseLong(s[1]);
        time = Integer.parseInt(s[2]);
        if (event instanceof GroupMessageEvent event1) {
            Objects.requireNonNull(event1.getGroup().get(id)).mute(time);
        } else {
            AtomicReference<NormalMember> member = new AtomicReference<>();
            GroupList.INSTANCE.getGroup().forEach(v -> member.set(Objects.requireNonNull(event.getBot().getGroup(v)).get(id)));
            if (member.get().getPermission().getLevel() < 1) {
                member.get().unmute();
            }
        }
    }

    @Command(name = "禁言操作", dec = "禁言群", help = "/mute group [id]")
    @Message(text = "/mute group\\s+?\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void muteGroup(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        Objects.requireNonNull(event.getBot().getGroup(id)).getSettings().setMuteAll(true);
    }

    @Command(name = "禁言操作", dec = "解禁群", help = "/unmute [id]")
    @Message(text = "/unmute group\\s+?\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void unmuteGroup(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        Objects.requireNonNull(event.getBot().getGroup(id)).getSettings().setMuteAll(false);
    }
}
