package com.erzbir.numeron.api.filter;

import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Date 2023/8/11
 */
public class DefaultFilter implements CustomFilter {
    @Override
    public <E extends Event> boolean filter(E event) {
        return true;
    }
}
