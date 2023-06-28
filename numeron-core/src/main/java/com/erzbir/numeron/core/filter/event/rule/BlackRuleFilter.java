package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.entity.serviceimpl.BlackServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.GroupServiceImpl;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>过滤黑名单</p>
 */
public class BlackRuleFilter extends AbstractRuleFilter implements EventFilter<MessageEvent> {
    public BlackRuleFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        BlackServiceImpl blackService = new BlackServiceImpl();
        GroupServiceImpl groupService = new GroupServiceImpl();
        return event instanceof GroupMessageEvent event1 ? !blackService.exist(event.getSender().getId()) && groupService.exist(event1.getGroup().getId())
                : !blackService.exist(event.getSender().getId());
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
