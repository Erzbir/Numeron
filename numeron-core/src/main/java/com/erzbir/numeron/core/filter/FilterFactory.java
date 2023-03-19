package com.erzbir.numeron.core.filter;

/**
 * @author Erzbir
 * @Date: 2022/11/26 17:54
 */
public interface FilterFactory {
    ChannelFilterInter create(Enum<?> e);
}
