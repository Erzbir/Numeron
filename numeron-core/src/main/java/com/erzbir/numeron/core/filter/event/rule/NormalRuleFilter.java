package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.entity.serviceimpl.GroupServiceImpl;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>过滤掉为授权的群</p>
 */
public class NormalRuleFilter extends AbstractRuleFilter implements Filter<MessageEvent> {

    @Override
    public EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel) {
        return filter0(channel);
    }

    private EventChannel<? extends MessageEvent> filter0(EventChannel<? extends MessageEvent> channel) {
        GroupServiceImpl groupService = new GroupServiceImpl();
        return channel.filter(event -> {
            if (event instanceof GroupMessageEvent event1) {
                return groupService.exist(event1.getGroup().getId());
            }
            return true;
        });
    }
}
