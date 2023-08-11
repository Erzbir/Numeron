package com.erzbir.numeron.api.filter.permission;

import com.erzbir.numeron.api.filter.AbstractMessageEventChannelFilter;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * <p>抽象权限过滤类, 根据规则过滤channel</p>
 */
public abstract class AbstractPermissionChannelFilter extends AbstractMessageEventChannelFilter implements ChannelFilter<MessageEvent> {

}
