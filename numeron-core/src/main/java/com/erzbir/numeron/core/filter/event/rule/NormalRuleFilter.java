package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.entity.serviceimpl.GroupServiceImpl;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>过滤掉为授权的群</p>
 */
public class NormalRuleFilter extends AbstractRuleFilter implements EventFilter<MessageEvent> {

    public NormalRuleFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        GroupServiceImpl groupService = new GroupServiceImpl();
        if (event instanceof GroupMessageEvent event1) {
            return groupService.exist(event1.getGroup().getId());
        }
        return true;
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }

}
