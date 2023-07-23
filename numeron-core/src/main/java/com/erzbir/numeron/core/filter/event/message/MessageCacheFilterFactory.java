package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.AbstractCacheFilterFactory;
import com.erzbir.numeron.core.filter.CacheFilterFactory;
import com.erzbir.numeron.core.filter.FilterFactory;
import com.erzbir.numeron.filter.MessageRule;

import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:58
 * <p>
 * 消息过滤类工厂
 * </p>
 */
public class MessageCacheFilterFactory extends AbstractCacheFilterFactory implements FilterFactory, CacheFilterFactory {
    public final static MessageCacheFilterFactory INSTANCE = new MessageCacheFilterFactory();
    private MessageCacheFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public AbstractMessageFilter create(Enum<?> e) {
        if (e.equals(MessageRule.EQUAL)) {
            return (AbstractMessageFilter) getFilter(EqualsMessageFilter.class);
        } else if (e.equals(MessageRule.END_WITH)) {
            return (AbstractMessageFilter) getFilter(EndMessageFilter.class);
        } else if (e.equals(MessageRule.BEGIN_WITH)) {
            return (AbstractMessageFilter) getFilter(BeginMessageFilter.class);
        } else if (e.equals(MessageRule.CONTAINS)) {
            return (AbstractMessageFilter) getFilter(ContainsMessageFilter.class);
        } else if (e.equals(MessageRule.REGEX)) {
            return (AbstractMessageFilter) getFilter(RegexMessageFilter.class);
        } else if (e.equals(MessageRule.IN)) {
            return (AbstractMessageFilter) getFilter(InMessageFilter.class);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
