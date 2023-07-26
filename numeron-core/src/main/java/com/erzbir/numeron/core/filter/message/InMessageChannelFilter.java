package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/27 01:25
 * <p>实体消息过滤类, 此类是判断是否在text中</p>
 */
public class InMessageChannelFilter extends AbstractMessageChannelFilter implements ChannelFilter<MessageEvent> {

    @Override
    public boolean filter(MessageEvent event) {
        return text.isEmpty() || List.of(text.split(",\\s+")).contains(event.getMessage().contentToString());
    }
}
