package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.AbstractMessageEventFilter;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 抽象消息过滤类
 * <p></p>
 * 继承此抽象类用于实现对消息内容的过滤
 *
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * @see AbstractMessageEventFilter
 * @see EventFilter
 */
public abstract class AbstractMessageFilter extends AbstractMessageEventFilter implements EventFilter<MessageEvent> {
    /**
     * 此参数用于记录文本的规则
     */
    protected String text = "";

    public AbstractMessageFilter(Filter filter) {
        super(filter);
    }

    public String getText() {
        return text;
    }

    public AbstractMessageFilter setText(String text) {
        this.text = text;
        return this;
    }
}
