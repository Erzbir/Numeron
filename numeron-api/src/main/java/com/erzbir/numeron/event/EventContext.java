package com.erzbir.numeron.event;

import com.erzbir.numeron.common.AttributeContainer;

/**
 * @author Erzbir
 * @Data: 2024/2/3 11:42
 */
public interface EventContext extends AttributeContainer<Object, Object> {
    Event getEvent();
}
