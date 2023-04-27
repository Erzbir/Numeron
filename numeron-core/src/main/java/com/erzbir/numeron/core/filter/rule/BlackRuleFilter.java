package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.api.model.BlackService;
import com.erzbir.numeron.api.model.GroupService;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>过滤黑名单</p>
 */
public class BlackRuleFilter extends AbstractRuleFilter {
    @Override
    public Boolean filter(MessageEvent event) {
        return event instanceof GroupMessageEvent event1 ? !BlackService.INSTANCE.exist(event.getSender().getId()) && GroupService.INSTANCE.exist(event1.getGroup().getId())
                : !BlackService.INSTANCE.exist(event.getSender().getId());
    }
}
