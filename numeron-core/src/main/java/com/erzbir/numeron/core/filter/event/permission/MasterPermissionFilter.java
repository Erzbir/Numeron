package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>主人权限过滤类, 过滤掉(舍弃)不是主人的event</p>
 */
public class MasterPermissionFilter extends AbstractPermissionFilter implements EventFilter<MessageEvent> {

    public MasterPermissionFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return BotServiceImpl.INSTANCE.getMaster(event.getBot()) == event.getSender().getId();
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
