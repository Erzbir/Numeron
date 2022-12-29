package com.erzbir.mirai.numeron.filter.message;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是判断是否包含text
 * </p>
 */
public class ContainsMessageFilter extends AbstractMessageFilter {
    public static final ContainsMessageFilter INSTANCE = new ContainsMessageFilter();

    private ContainsMessageFilter() {

    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event.getMessage().contentToString().contains(text);
    }
}
