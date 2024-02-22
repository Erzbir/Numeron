package com.erzbir.numeron.application;

import com.erzbir.numeron.bot.BotManager;
import com.erzbir.numeron.component.Component;
import com.erzbir.numeron.event.EventDispatcher;

import java.util.List;

/**
 * @author Erzbir
 * @Data: 2024/2/8 00:41
 */
public interface Application {
    AppConfiguration getConfiguration();

    EventDispatcher getEventDispatcher();

    List<Component> getComponents();

    BotManager getBotManager();

    void join();
}
