package com.erzbir.mirai.numeron.filter.rule;

import com.erzbir.mirai.numeron.entity.BlackList;
import com.erzbir.mirai.numeron.entity.GroupList;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * 实体规则过滤类
 */
public class BlackRuleFilter extends AbstractRuleFilter {
    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event instanceof GroupMessageEvent event1
                ?
                !BlackList.INSTANCE.contains(event.getSender().getId()) && GroupList.INSTANCE.contains(event1.getGroup().getId())
                :
                !BlackList.INSTANCE.contains(event.getSender().getId());
    }
}
