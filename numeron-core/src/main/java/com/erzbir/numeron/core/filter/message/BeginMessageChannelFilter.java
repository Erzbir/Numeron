package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息过滤装饰类, 此类是判断是否以text开头
 *
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * @see AbstractMessageChannelFilter
 */
public class BeginMessageChannelFilter extends AbstractMessageChannelFilter implements ChannelFilter<MessageEvent> {

    @Override
    public boolean filter(MessageEvent event) {
        return text.isEmpty() || event.getMessage().contentToString().startsWith(text);
    }
}
