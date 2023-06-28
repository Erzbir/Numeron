package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/27 01:25
 * <p>实体消息过滤类, 此类是判断是否在text中</p>
 */
public class InMessageFilter extends AbstractMessageFilter implements EventFilter<MessageEvent> {


    public InMessageFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return text.isEmpty() || List.of(text.split(",\\s+")).contains(event.getMessage().contentToString());
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
