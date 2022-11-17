package com.erzbir.mirai.numeron.manage;

import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: Erzbir
 * @Date: 2022/11/13 23:03
 */
public class GroupManager {
    public final static GroupManager INSTANCE = new GroupManager();

    public void userJoin(@NotNull MemberJoinEvent event) {
        event.getGroup().sendMessage("欢迎入群~");
    }

    public void userRequest(MemberJoinRequestEvent event) {
    }

    public void userLeave(@NotNull MemberLeaveEvent event) {
        event.getGroup().sendMessage(event.getMember() + "离开了我们");
    }
}
