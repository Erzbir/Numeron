package com.erzbir.numeron.api.filter.message;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>
 * 实体消息过滤类, 此类是消息是否满足正则表达式(text)
 * </p>
 */
public class RegexMessageChannelFilter extends AbstractMessageChannelFilter implements ChannelFilter<MessageEvent> {

    @Override
    public boolean filter(MessageEvent event) {
        return text.isEmpty() || Pattern.compile(text).matcher(event.getMessage().contentToString()).find();
    }
}
