package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/27 01:25
 * <p>实体消息过滤类, 此类是判断是否在text中</p>
 */
public class InMessageFilter extends AbstractMessageFilter implements Filter<MessageEvent> {
    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        return channel.filter(event -> text.isEmpty() || List.of(text.split(",\\s+")).contains(event.getMessage().contentToString()));
    }
}
