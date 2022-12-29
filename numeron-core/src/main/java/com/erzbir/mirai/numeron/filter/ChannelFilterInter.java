package com.erzbir.mirai.numeron.filter;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:18
 * <p>
 * 注解处理过滤器接口
 * </p>
 */
public interface ChannelFilterInter {

    /**
     * @param event 消息事件
     * @return Boolean
     */
    Boolean filter(MessageEvent event, String text);
}
