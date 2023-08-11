package com.erzbir.numeron.api.filter.rule;

import com.erzbir.numeron.api.entity.BlackService;
import com.erzbir.numeron.api.entity.BlackServiceImpl;
import com.erzbir.numeron.api.entity.GroupService;
import com.erzbir.numeron.api.entity.GroupServiceImpl;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>过滤黑名单</p>
 */
public class BlackRuleChannelFilter extends AbstractRuleChannelFilter {
    private final BlackService blackService = BlackServiceImpl.INSTANCE;
    private final GroupService groupService = GroupServiceImpl.INSTANCE;

    @Override
    public boolean filter(MessageEvent event) {
        return event instanceof GroupMessageEvent event1 ? !blackService.exist(event.getSender().getId()) && groupService.exist(event1.getGroup().getId())
                : !blackService.exist(event.getSender().getId());
    }
}
