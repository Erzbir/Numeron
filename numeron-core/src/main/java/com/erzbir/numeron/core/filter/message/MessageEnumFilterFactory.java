package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.EnumFilterFactory;
import com.erzbir.numeron.filter.MessageRule;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:58
 * <p>
 * 消息过滤类工厂
 * </p>
 */
public class MessageEnumFilterFactory implements EnumFilterFactory {
    public final static MessageEnumFilterFactory INSTANCE = new MessageEnumFilterFactory();

    private MessageEnumFilterFactory() {

    }

    @Override
    public AbstractMessageChannelFilter create(Enum<?> e) {
        if (e.equals(MessageRule.EQUAL)) {
            return new EqualsMessageChannelFilter();
        } else if (e.equals(MessageRule.END_WITH)) {
            return new EndMessageChannelFilter();
        } else if (e.equals(MessageRule.BEGIN_WITH)) {
            return new BeginMessageChannelFilter();
        } else if (e.equals(MessageRule.CONTAINS)) {
            return new ContainsMessageChannelFilter();
        } else if (e.equals(MessageRule.REGEX)) {
            return new RegexMessageChannelFilter();
        } else if (e.equals(MessageRule.IN)) {
            return new InMessageChannelFilter();
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
