package com.erzbir.mirai.numeron.filter.message;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * <p>
 * 实体消息过滤类, 此类是判断是否以text结尾
 * </p>
 */
public class EndMessageFilter extends AbstractMessageFilter {
    public static final EndMessageFilter INSTANCE = new EndMessageFilter();

    private EndMessageFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event.getMessage().contentToString().endsWith(text);
    }
}
