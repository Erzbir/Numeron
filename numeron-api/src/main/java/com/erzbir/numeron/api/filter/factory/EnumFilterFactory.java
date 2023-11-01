package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.ChannelFilter;
import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.event.Event;

/**
 * <p>
 * 实现此接口以枚举创建 {@link ChannelFilter}
 * </p>
 *
 * @author Erzbir
 * @Date 2023/7/26
 */
@FunctionalInterface
public interface EnumFilterFactory extends FilterFactory<Enum<?>> {
    Filter<? extends Event> create(Enum<?> e);
}
