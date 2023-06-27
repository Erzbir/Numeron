package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是判断是否包含text
 * </p>
 */
public class ContainsMessageFilter extends AbstractMessageFilter implements EventFilter<MessageEvent> {

    public ContainsMessageFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return text.isEmpty() || event.getMessage().contentToString().contains(text);
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }

}
