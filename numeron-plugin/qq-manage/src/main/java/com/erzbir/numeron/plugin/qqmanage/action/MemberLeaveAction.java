package com.erzbir.numeron.plugin.qqmanage.action;


import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.api.filter.enums.MatchType;
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

    @Handler
    public void register(MemberLeaveEvent event) {
        if (isOn.containsKey(event.getGroupId())) {
            event.getGroup().sendMessage(event.getMember().getNick() + " 离开了我们...");
        }
    }

    @Command(
            name = "退群提示",
            dec = "开关退群",
            help = "/leave feedback [true|false]",
            permission = PermissionType.ADMIN
    )
    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "/leave\\s+?feedback\\s+?(true|false)", matchType = MatchType.REGEX_MATCHES)
    private void onOff(GroupMessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+?");
        isOn.put(event.getGroup().getId(), Boolean.parseBoolean(s[2]));
        event.getSubject().sendMessage(isOn.toString());
    }
}
