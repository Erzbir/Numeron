package com.erzbir.numeron.core.filter;

import net.mamoe.mirai.event.Event;

/**
 * 此接口的抽象方法用于实现真正的过滤规则和传递 {@code Predicate} 所需要的参数
 * <p></p>
 * 这样设计的目的是为了隐鲹过滤规则的构造, 只把过滤需要的参数对外开放
 *
 * @author Erzbir
 * @Date: 2022/11/16 22:18
 * @see Filter
 */
public interface EventFilter<E extends Event> extends Filter {

    /**
     * <p>
     * 此方法的
     * </p>
     *
     * @param event 事件
     */
    void filter(E event);
}
