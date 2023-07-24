package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>所有人权限过滤类, 此类是所有人权限不做过滤</p>
 */
public class AllPermissionChannelFilter extends AbstractPermissionChannelFilter implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        return true;
    }
}
