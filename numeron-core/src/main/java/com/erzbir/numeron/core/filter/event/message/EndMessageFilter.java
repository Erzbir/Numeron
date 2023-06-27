package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * <p>
 * 实体消息过滤类, 此类是判断是否以text结尾
 * </p>
 */
public class EndMessageFilter extends AbstractMessageFilter implements EventFilter<MessageEvent> {
    public EndMessageFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return text.isEmpty() || event.getMessage().contentToString().endsWith(text);
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
