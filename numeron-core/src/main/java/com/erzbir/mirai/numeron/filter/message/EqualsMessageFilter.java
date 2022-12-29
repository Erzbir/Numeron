package com.erzbir.mirai.numeron.filter.message;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是判断是否与text相等
 * </p>
 */
public class EqualsMessageFilter extends AbstractMessageFilter {
    public static final EqualsMessageFilter INSTANCE = new EqualsMessageFilter();

    private EqualsMessageFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event.getMessage().contentToString().equals(text);
    }
}
