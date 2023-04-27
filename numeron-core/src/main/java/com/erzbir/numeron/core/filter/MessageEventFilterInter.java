package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/4/27 09:11
 * <p>
 * 消息过滤接口
 * </p>
 */
public interface MessageEventFilterInter {
    Boolean filter(MessageEvent event);
}
