package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.entity.BlackList;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 16:51
 * <p>好友权限过滤类, 过滤掉(舍弃)不是好友的event</p>
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
