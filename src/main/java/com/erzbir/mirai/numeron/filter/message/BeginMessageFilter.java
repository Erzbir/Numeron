package com.erzbir.mirai.numeron.filter.message;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:55
 * <p>
 * 实体消息过滤类, 此类是判断是否以text开头
 * </p>
 */
public class BeginMessageFilter extends AbstractMessageFilter {
    public static final BeginMessageFilter INSTANCE = new BeginMessageFilter();

    private BeginMessageFilter() {

    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event.getMessage().contentToString().startsWith(text);
    }
}
