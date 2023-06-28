package com.erzbir.numeron.core.filter;


/**
 * @author Erzbir
 * @Date: 2022/11/26 17:54
 */
@FunctionalInterface
public interface FilterFactory {
    Filter create(Enum<?> e, Filter eventFilter);
}
