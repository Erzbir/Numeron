package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.entity.serviceimpl.BlackServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.GroupServiceImpl;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>过滤黑名单</p>
 */
public class BlackRuleFilter extends AbstractRuleFilter {

    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        BlackServiceImpl blackService = new BlackServiceImpl();
        GroupServiceImpl groupService = new GroupServiceImpl();
        return channel.filter(event -> event instanceof GroupMessageEvent event1 ? !blackService.exist(event.getSender().getId()) && groupService.exist(event1.getGroup().getId())
                : !blackService.exist(event.getSender().getId()));
    }
}
