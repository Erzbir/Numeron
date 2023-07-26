package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>不做过滤</p>
 */
public class NoneRuleChannelFilter extends AbstractRuleChannelFilter implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        return true;
    }
}
