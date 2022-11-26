package com.erzbir.mirai.numeron.filter.rule;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * 实体规则过滤类
 */
public class BlackRuleFilter extends AbstractFilterRuleFilter {
    @Override
    public Boolean filter(MessageEvent event, String text) {
        return !GlobalConfig.blackList.contains(event.getSender().getId());
    }
}
