package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/6/25 15:08
 */
public class IDMessageFilter extends AbstractMessageFilter implements EventFilter<MessageEvent> {
    public IDMessageFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return id == 0 || id == event.getSender().getId();
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
