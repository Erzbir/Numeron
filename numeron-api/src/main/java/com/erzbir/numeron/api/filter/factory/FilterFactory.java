package com.erzbir.numeron.api.filter.factory;


import com.erzbir.numeron.api.filter.Filter;
import net.mamoe.mirai.event.Event;

/**
 * <p>
 * 顶层过滤器工厂父类
 * </p>
 *
 * @author Erzbir
 * @Date: 2022/11/26 17:54
 */
public interface FilterFactory<E> {
    Filter<? extends Event> create(E e);
}
