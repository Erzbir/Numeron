package com.erzbir.numeron.api.filter.factory;

import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.event.Event;

/**
 * <p>
 * 缓存工厂, 第一次创建之后缓存到容器中
 * </P>
 *
 * @author Erzbir
 * @Date 2023/7/23
 */
public interface CacheFilterFactory {
    Filter<? extends Event> getFilter(Class<? extends Filter<? extends Event>> filerClass);
}
