package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.filter.AbstractMessageEventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:48
 * <p>抽象规则过滤类</p>
 */
public abstract class AbstractRuleFilter extends AbstractMessageEventFilter implements Filter<MessageEvent> {

    public abstract EventChannel<? extends MessageEvent> filter(EventChannel<? extends MessageEvent> channel);
}
