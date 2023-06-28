package com.erzbir.numeron.core.filter.event.rule;

import com.erzbir.numeron.core.filter.AbstractMessageEventFilter;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:48
 * <p>抽象规则过滤类</p>
 */
public abstract class AbstractRuleFilter extends AbstractMessageEventFilter implements EventFilter<MessageEvent> {

    public AbstractRuleFilter(Filter filter) {
        super(filter);
    }
}
