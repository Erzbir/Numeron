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
    public static MessageFilterFactory INSTANCE = new MessageFilterFactory();

    private MessageFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public ChannelFilterInter create(Enum<?> e) {
        if (e.equals(EQUAL)) {
            return new EqualsMessageFilter();
        } else if (e.equals(END_WITH)) {
            return new EndMessageFilter();
        } else if (e.equals(BEGIN_WITH)) {
            return new BeginMessageFilter();
        } else if (e.equals(CONTAINS)) {
            return new ContainsMessageFilter();
        } else if (e.equals(REGEX)) {
            return new RegexMessageFilter();
        } else if (e.equals(IN)) {
            return new InMessageFilter();
        }
        return null;
    }
}
