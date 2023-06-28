package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是消息是否满足正则表达式(text)
 * </p>
 */
public class RegexMessageFilter extends AbstractMessageFilter implements EventFilter<MessageEvent> {

    public RegexMessageFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private boolean filter0(MessageEvent event) {
        return text.isEmpty() || Pattern.compile(text).matcher(event.getMessage().contentToString()).find();
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}
