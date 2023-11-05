package com.erzbir.numeron.api.filter.factory;


import com.erzbir.numeron.api.filter.Filter;

/**
 * <p>
 * 顶层过滤器工厂接口
 * </p>
 *
 * @author Erzbir
 * @Date: 2022/11/26 17:54
 */
public interface FilterFactory {
    <E> Filter create(E e);
}
