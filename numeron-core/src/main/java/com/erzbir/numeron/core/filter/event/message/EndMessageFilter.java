package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * <p>
 * 实体消息过滤类, 此类是判断是否以text结尾
 * </p>
 */
public class EndMessageFilter extends AbstractMessageFilter implements Filter<MessageEvent> {
    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        return channel.filter(event -> text.isEmpty() || event.getMessage().contentToString().endsWith(text));
    }
}
