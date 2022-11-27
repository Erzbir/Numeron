package com.erzbir.mirai.numeron.filter.rule;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * 实体规则过滤类
 */
public class NoneRuleFilter extends AbstractRuleFilter {

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return true;
    }
}
