package com.erzbir.numeron.console.plugin;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:18
 */
public interface Plugin {
    void onEnable();

    void onDisable();

    void onLoad();

    void onUnLoad();

    void enable();

    void disable();

    boolean isEnable();

    NumeronDescription getDescription();

}
