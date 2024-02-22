package com.erzbir.numeron.component;

/**
 * @author Erzbir
 * @Data: 2024/2/7 19:19
 */
public interface ComponentManager {
    void addComponent();

    void removeComponent();

    Component getComponent(String id);

}
