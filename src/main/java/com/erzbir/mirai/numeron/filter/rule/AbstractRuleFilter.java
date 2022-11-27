package com.erzbir.mirai.numeron.filter.rule;

import com.erzbir.mirai.numeron.filter.ChannelFilterInter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:48
 * 抽象规则过滤类
 */
public abstract class AbstractRuleFilter implements ChannelFilterInter {

    @Override
    public abstract Boolean filter(MessageEvent event, String text);
}
