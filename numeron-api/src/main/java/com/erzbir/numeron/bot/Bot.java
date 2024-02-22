package com.erzbir.numeron.bot;

import com.erzbir.numeron.component.Component;
import com.erzbir.numeron.entity.Subject;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:03
 */
public interface Bot extends Subject {
    String getName();

    Component getComponent();

    void start();

    void join();

    void cancel();
}
