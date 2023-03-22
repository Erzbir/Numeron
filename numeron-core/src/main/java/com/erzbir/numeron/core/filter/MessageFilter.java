package com.erzbir.numeron.core.filter;

import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;

/**
 * @author Erzbir
 * @Date: 2023/3/22 11:21
 */
public @interface MessageFilter {
    FilterRule filterRule() default FilterRule.NONE;

    MessageRule messageRule() default MessageRule.EQUAL;

    PermissionType permission();

    String text();

    EventPriority priority() default EventPriority.NORMAL;

    boolean ignoreCancelled() default true;

    ConcurrencyKind concurrency() default ConcurrencyKind.CONCURRENT;
}
