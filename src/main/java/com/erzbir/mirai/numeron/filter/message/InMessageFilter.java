package com.erzbir.mirai.numeron.filter.message;

import net.mamoe.mirai.event.events.MessageEvent;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2022/11/27 01:25
 */
public class InMessageFilter extends AbstractMessageFilter {
    public static final InMessageFilter INSTANCE = new InMessageFilter();

    private InMessageFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return List.of(text.split(",\\s+")).contains(event.getMessage().contentToString());
    }
}
