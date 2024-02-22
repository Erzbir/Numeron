package com.erzbir.numeron.application;

import com.erzbir.numeron.bot.BotManager;
import com.erzbir.numeron.component.Component;
import com.erzbir.numeron.event.PollingEventDispatcher;
import com.erzbir.numeron.event.EventDispatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Erzbir
 * @Data: 2024/2/8 01:26
 */
public class DefaultApplication implements Application {
    private AppConfiguration appConfiguration;
    private EventDispatcher eventDispatcher = new PollingEventDispatcher();
    private List<Component> components = new ArrayList<>();
    private BotManager botManager;

    @Override
    public AppConfiguration getConfiguration() {
        return appConfiguration;
    }

    @Override
    public EventDispatcher getEventDispatcher() {
        return eventDispatcher;
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public BotManager getBotManager() {
        return botManager;
    }

    @Override
    public void join() {

    }
}
