package com.erzbir.numeron.core.filter.rule;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>不做过滤</p>
 */
public class NoneRuleFilter extends AbstractRuleFilter {

    @Override
    public Boolean filter(MessageEvent event) {
        return true;
    }
}
