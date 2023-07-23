package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>不做过滤</p>
 */
public class NoneRuleFilter extends AbstractRuleFilter implements Filter<MessageEvent> {
    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return channel;
    }

}
