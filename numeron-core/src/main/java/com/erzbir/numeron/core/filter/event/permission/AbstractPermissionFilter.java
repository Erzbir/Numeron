package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.core.filter.AbstractMessageEventFilter;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:47
 * <p>抽象权限过滤类, 根据规则过滤channel</p>
 */
public abstract class AbstractPermissionFilter extends AbstractMessageEventFilter implements EventFilter<MessageEvent> {

    public AbstractPermissionFilter(Filter filter) {
        super(filter);
    }
}
