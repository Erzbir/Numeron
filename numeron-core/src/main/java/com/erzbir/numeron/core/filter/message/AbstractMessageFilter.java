package com.erzbir.numeron.core.filter.message;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * <p>
 * 抽象消息过滤类
 * </p>
 */
public abstract class AbstractMessageFilter implements ChannelFilterInter {

    @Override
    public abstract Boolean filter(MessageEvent event, String text);
}
