package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import com.erzbir.numeron.core.filter.FilterFactory;

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
    public ChannelFilterInter create(Enum<?> e) {
        if (e.equals(MessageRule.EQUAL)) {
            return EqualsMessageFilter.INSTANCE;
        } else if (e.equals(MessageRule.END_WITH)) {
            return EndMessageFilter.INSTANCE;
        } else if (e.equals(MessageRule.BEGIN_WITH)) {
            return BeginMessageFilter.INSTANCE;
        } else if (e.equals(MessageRule.CONTAINS)) {
            return ContainsMessageFilter.INSTANCE;
        } else if (e.equals(MessageRule.REGEX)) {
            return RegexMessageFilter.INSTANCE;
        } else if (e.equals(MessageRule.IN)) {
            return InMessageFilter.INSTANCE;
        }
        return null;
    }
}
