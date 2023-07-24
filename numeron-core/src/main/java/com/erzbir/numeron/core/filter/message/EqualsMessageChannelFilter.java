package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是判断是否与text相等
 * </p>
 */
public class EqualsMessageChannelFilter extends AbstractMessageChannelFilter implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        return text.isEmpty() || event.getMessage().contentToString().equals(text);
    }
}
