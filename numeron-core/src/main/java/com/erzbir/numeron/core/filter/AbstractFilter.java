package com.erzbir.numeron.core.filter;

import java.util.function.Predicate;

/**
 * 此抽象类为策略模式的体现,
 * 逻辑是继承此类的子类会带有一个 {@code Predicate<Object>} 参数 用于返回布尔值和一个 {@code Object} 参数
 * 供 {@code Predicate<Object>} 使用. 这样可以在以后的子类中通过实现 {@code Predicate<Object>} 来实现不同的过滤逻辑,
 * 最终由顶层接口 {@code Filter} 的抽象方法返回 {@code predicate.test(arg)} 的值来完成过滤
 *
 * @author Erzbir
 * @Date: 2023/6/27 11:58
 * @see Filter
 */
public abstract class AbstractFilter implements Filter {
    protected Predicate<Object> filterRule;
    protected Object arg;

    /**
     * 讲此方法继承给所有过滤器子类, 调用此方法设置过滤规则,
     * 最种由 {@code Filter} 的抽象方法调用 {@code {@param predicate}.{@param arg}} 返回过滤是否成立
     *
     * @param predicate 过滤规则
     * @param arg       为 {@code Predicate<Object>} 提供参数
     */
    protected <E> void setFilterRule(Predicate<E> predicate, E arg) {
        this.filterRule = (Predicate<Object>) predicate;
        this.arg = arg;
    }

    public <E> Predicate<E> getFilterRule() {
        return (Predicate<E>) filterRule;
    }
}
