package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.entity.serviceimpl.BlackServiceImpl;
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
    public Boolean filter(MessageEvent event) {
        BlackServiceImpl blackService = new BlackServiceImpl();
        return event instanceof FriendMessageEvent && !blackService.exist(event.getSender().getId());
    }
}
