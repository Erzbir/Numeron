package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.entity.BlackList;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 16:51
 */
public class FriendPermissionFilter extends AbstractPermissionFilter {
    public static final FriendPermissionFilter INSTANCE = new FriendPermissionFilter();

    private FriendPermissionFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event instanceof FriendMessageEvent && !BlackList.INSTANCE.contains(event.getSender().getId());
    }
}
