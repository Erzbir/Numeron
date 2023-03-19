package com.erzbir.numeron.core.filter.rule;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:48
 * <p>抽象规则过滤类</p>
 */
public abstract class AbstractRuleFilter implements ChannelFilterInter {

    @Override
    public abstract Boolean filter(MessageEvent event, String text);
}
