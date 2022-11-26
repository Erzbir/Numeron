package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.filter.ChannelFilterInter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * 抽象权限过滤类
 */
public abstract class AbstractPermissionFilter implements ChannelFilterInter {

    @Override
    public abstract Boolean filter(MessageEvent event, String text);
}
