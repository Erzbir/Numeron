package com.erzbir.numeron.core.filter.event.message;

import com.erzbir.numeron.core.filter.Filter;
import com.erzbir.numeron.core.filter.FilterFactory;
import com.erzbir.numeron.filter.MessageRule;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:58
 * <p>
 * 消息过滤类工厂
 * </p>
 */
public class MessageFilterFactory implements FilterFactory {
    public final static MessageFilterFactory INSTANCE = new MessageFilterFactory();

    private MessageFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public AbstractMessageFilter create(Enum<?> e, Filter filter) {
        if (e.equals(MessageRule.EQUAL)) {
            return new EqualsMessageFilter(filter);
        } else if (e.equals(MessageRule.END_WITH)) {
            return new EndMessageFilter(filter);
        } else if (e.equals(MessageRule.BEGIN_WITH)) {
            return new BeginMessageFilter(filter);
        } else if (e.equals(MessageRule.CONTAINS)) {
            return new ContainsMessageFilter(filter);
        } else if (e.equals(MessageRule.REGEX)) {
            return new RegexMessageFilter(filter);
        } else if (e.equals(MessageRule.IN)) {
            return new InMessageFilter(filter);
        }
        return null;
    }
}
