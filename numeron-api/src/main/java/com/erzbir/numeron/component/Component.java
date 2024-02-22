package com.erzbir.numeron.component;

import com.erzbir.numeron.common.LifeCycle;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:14
 */
public interface Component extends LifeCycle {
    String getName();

    String getId();
}
