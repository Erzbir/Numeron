package com.erzbir.numeron.api.filter;

/**
 * <p>
 * 顶层过滤器父接口
 * </p>
 *
 * @author Erzbir
 * @Data 2023/7/24
 */
@FunctionalInterface
public interface Filter {
    <E> boolean filter(E event);
}
