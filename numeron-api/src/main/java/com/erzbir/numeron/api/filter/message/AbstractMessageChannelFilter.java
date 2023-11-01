package com.erzbir.numeron.api.filter.message;

import com.erzbir.numeron.api.filter.AbstractMessageEventChannelFilter;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * 抽象消息过滤类
 * <p></p>
 * 继承此抽象类用于实现对消息内容的过滤
 *
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * @see AbstractMessageEventChannelFilter
 */
public abstract class AbstractMessageChannelFilter extends AbstractMessageEventChannelFilter implements ChannelFilter<MessageEvent> {
    /**
     * 此参数用于记录文本的规则
     */
    protected String text = "";

    public String getText() {
        return text;
    }

    public AbstractMessageChannelFilter setText(String text) {
        this.text = text;
        return this;
    }
}
