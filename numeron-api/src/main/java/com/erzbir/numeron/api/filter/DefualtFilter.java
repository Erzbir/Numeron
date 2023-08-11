package com.erzbir.numeron.api.filter;

import net.mamoe.mirai.event.Event;

/**
 * @author Erzbir
 * @Date 2023/8/11
 */
public class DefualtFilter implements CustomFilter<Event> {

    @Override
    public boolean filter(Event event) {
        return true;
    }
}
