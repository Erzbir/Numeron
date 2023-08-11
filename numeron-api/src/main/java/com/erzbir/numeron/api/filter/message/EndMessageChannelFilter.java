package com.erzbir.numeron.api.filter.message;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * <p>
 * 实体消息过滤类, 此类是判断是否以text结尾
 * </p>
 */
public class EndMessageChannelFilter extends AbstractMessageChannelFilter implements ChannelFilter<MessageEvent> {

    @Override
    public boolean filter(MessageEvent event) {
        return text.isEmpty() || event.getMessage().contentToString().endsWith(text);
    }
}
