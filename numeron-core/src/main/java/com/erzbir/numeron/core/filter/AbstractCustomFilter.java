package com.erzbir.numeron.core.filter;

import java.util.function.Predicate;

/**
 * 此类为自定义过滤抽象类
 *
 * @author Erzbir
 * @Date: 2023/6/27 13:59
 * @see CustomFilter
 * @see AbstractFilter
 */
public class AbstractCustomFilter extends AbstractFilter implements CustomFilter {
    protected Predicate<Object> filter;
    protected Object arg;


    @Override
    public <E> void filter(Predicate<E> predicate, E arg) {
        setFilterRule(predicate, arg);
    }

    @Override
    public boolean filter() {
        return filter.test(arg);
    }
}
