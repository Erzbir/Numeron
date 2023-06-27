package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>不做过滤</p>
 */
public class NoneRuleFilter extends AbstractRuleFilter implements EventFilter<MessageEvent> {

    public NoneRuleFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> true, event);
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
