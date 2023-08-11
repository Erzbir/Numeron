package com.erzbir.numeron.api.filter.rule;

import com.erzbir.numeron.api.filter.AbstractMessageEventChannelFilter;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:48
 * <p>抽象规则过滤类</p>
 */
public abstract class AbstractRuleChannelFilter extends AbstractMessageEventChannelFilter implements ChannelFilter<MessageEvent> {

}
