package com.erzbir.mirai.numeron.filter.message;

import com.erzbir.mirai.numeron.filter.ChannelFilterInter;
import com.erzbir.mirai.numeron.filter.FilterFactory;

import static com.erzbir.mirai.numeron.enums.MessageRule.*;

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
        if (e.equals(EQUAL)) {
            return EqualsMessageFilter.INSTANCE;
        } else if (e.equals(END_WITH)) {
            return EndMessageFilter.INSTANCE;
        } else if (e.equals(BEGIN_WITH)) {
            return BeginMessageFilter.INSTANCE;
        } else if (e.equals(CONTAINS)) {
            return ContainsMessageFilter.INSTANCE;
        } else if (e.equals(REGEX)) {
            return RegexMessageFilter.INSTANCE;
        } else if (e.equals(IN)) {
            return InMessageFilter.INSTANCE;
        }
        return null;
    }
}
