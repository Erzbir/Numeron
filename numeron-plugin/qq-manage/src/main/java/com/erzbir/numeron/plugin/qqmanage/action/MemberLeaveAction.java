package com.erzbir.numeron.plugin.qqmanage.action;


import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Event;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.filter.MessageRule;
import com.erzbir.numeron.api.filter.PermissionType;
import com.erzbir.numeron.menu.Menu;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:07
 */
@Listener
@Menu(name = "退群提示")
@SuppressWarnings("unused")
public class MemberLeaveAction {
    private final HashMap<Long, Boolean> isOn = new HashMap<>();

    @Event
    public void register(MemberLeaveEvent event) {
        if (isOn.containsKey(event.getGroupId())) {
            event.getGroup().sendMessage(event.getMember().getNick() + " 离开了我们...");
        }
    }

    @Command(
            name = "退群提示",
            dec = "开关退群",
            help = "/leave [true|false]",
            permission = PermissionType.ADMIN
    )
    @Message(
            text = "/leave\\s+?feedback\\s+?(true|false)",
            permission = PermissionType.ADMIN,
            messageRule = MessageRule.REGEX
    )
    private void onOff(GroupMessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+?");
        isOn.put(event.getGroup().getId(), Boolean.parseBoolean(s[2]));
        event.getSubject().sendMessage(isOn.toString());
    }
}
