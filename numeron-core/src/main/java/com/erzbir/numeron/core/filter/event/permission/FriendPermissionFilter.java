package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.core.entity.serviceimpl.BlackServiceImpl;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 16:51
 * <p>好友权限过滤类, 过滤掉(舍弃)不是好友的event</p>
 */
public class FriendPermissionFilter extends AbstractPermissionFilter implements Filter<MessageEvent> {
    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    public EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        BlackServiceImpl blackService = new BlackServiceImpl();
        return channel.filter(event -> event instanceof FriendMessageEvent && !blackService.exist(event.getSender().getId()));
    }
}
