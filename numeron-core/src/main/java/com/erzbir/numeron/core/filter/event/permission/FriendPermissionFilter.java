package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.core.entity.serviceimpl.BlackServiceImpl;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 16:51
 * <p>好友权限过滤类, 过滤掉(舍弃)不是好友的event</p>
 */
public class FriendPermissionFilter extends AbstractPermissionFilter implements EventFilter<MessageEvent> {

    public FriendPermissionFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    public boolean filter0(MessageEvent event) {
        BlackServiceImpl blackService = new BlackServiceImpl();
        return event instanceof FriendMessageEvent && !blackService.exist(event.getSender().getId());
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
