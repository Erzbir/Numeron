package com.erzbir.mirai.numeron.qqmanage;

import com.erzbir.mirai.numeron.annotation.litener.Listener;
import com.erzbir.mirai.numeron.annotation.massage.Message;
import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Erzbir
 * @Date: 2022/11/21 23:07
 */
@Listener
public class Mute {

    @Message (filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER, text = "/mute @?(\\d+?) (\\d+)")
    public void muteSingle(MessageEvent event) {

        String[] s = event.getMessage().contentToString().split(" ");
        long id = 0;
        int time = 0;
        s[1] = s[1].replaceAll("@", "");
        id = Long.parseLong(s[1]);
        time = Integer.parseInt(s[2]);
        if (event instanceof GroupMessageEvent event1) {
            Objects.requireNonNull(event1.getGroup().get(id)).mute(time);
        } else {
            AtomicReference<NormalMember> member = new AtomicReference<>();
            long finalId = id;
            GlobalConfig.groupList.forEach(v -> {
                member.set(Objects.requireNonNull(event.getBot().getGroup(v)).get(finalId));
            });
            if (member.get().getPermission().getLevel() < 1) {
                member.get().mute(time);
            }
        }

    }
}
