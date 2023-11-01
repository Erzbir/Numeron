package com.erzbir.numeron.api.filter.rule;

import com.erzbir.numeron.api.entity.GroupService;
import com.erzbir.numeron.api.entity.GroupServiceImpl;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>过滤掉为授权的群</p>
 */
public class NormalRuleChannelFilter extends AbstractRuleChannelFilter implements ChannelFilter<MessageEvent> {
    private final GroupService groupService = GroupServiceImpl.INSTANCE;

    @Override
    public boolean filter(MessageEvent event) {
        if (event instanceof GroupMessageEvent event1) {
            return groupService.exist(event1.getGroup().getId());
        }
        return true;
    }
}
