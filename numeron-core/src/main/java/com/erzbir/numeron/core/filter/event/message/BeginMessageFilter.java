package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 消息过滤装饰类, 此类是判断是否以text开头
 *
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * @see AbstractMessageFilter
 * @see EventFilter
 */
public class BeginMessageFilter extends AbstractMessageFilter implements EventFilter<MessageEvent> {

    public BeginMessageFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return text.isEmpty() || event.getMessage().contentToString().startsWith(text);
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
