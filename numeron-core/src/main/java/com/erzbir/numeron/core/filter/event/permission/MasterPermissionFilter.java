package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>主人权限过滤类, 过滤掉(舍弃)不是主人的event</p>
 */
public class MasterPermissionFilter extends AbstractPermissionFilter implements Filter<MessageEvent> {
    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        return channel.filter(event -> BotServiceImpl.INSTANCE.getMaster(event.getBot()) == event.getSender().getId());
    }
}
