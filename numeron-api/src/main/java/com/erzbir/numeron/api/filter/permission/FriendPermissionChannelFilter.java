package com.erzbir.numeron.api.filter.permission;

import com.erzbir.numeron.api.entity.BlackService;
import com.erzbir.numeron.api.entity.BlackServiceImpl;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 16:51
 * <p>好友权限过滤类, 过滤掉(舍弃)不是好友的event</p>
 */
public class FriendPermissionChannelFilter extends AbstractPermissionChannelFilter implements ChannelFilter<MessageEvent> {
    private final BlackService blackService = BlackServiceImpl.INSTANCE;


    @Override
    public boolean filter(MessageEvent event) {
        return event instanceof FriendMessageEvent && !blackService.exist(event.getSender().getId());
    }
}
