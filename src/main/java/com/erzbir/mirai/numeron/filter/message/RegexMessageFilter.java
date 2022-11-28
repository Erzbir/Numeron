package com.erzbir.mirai.numeron.filter.message;

import net.mamoe.mirai.event.events.MessageEvent;

import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是消息是否满足正则表达式(text)
 * </p>
 */
public class RegexMessageFilter extends AbstractMessageFilter {
    public static final RegexMessageFilter INSTANCE = new RegexMessageFilter();

    private RegexMessageFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return Pattern.compile(text).matcher(event.getMessage().contentToString()).find();
    }
}
