package com.erzbir.numeron.core.filter;

import java.util.function.Predicate;

/**
 * 此接口用于自定义过滤
 *
 * @author Erzbir
 * @Date: 2023/6/27 10:41
 */
public interface CustomFilter extends Filter {
    <E> void filter(Predicate<E> predicate, E arg);
}
