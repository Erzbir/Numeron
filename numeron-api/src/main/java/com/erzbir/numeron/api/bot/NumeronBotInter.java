package com.erzbir.numeron.api.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/5/2 00:37
 */
public interface NumeronBotInter {
    long getMaster();

    boolean isEnable();

    String getWorkDir();

    void shutdown();

    void launch();

    Bot getBot();

    EventChannel<BotEvent> getEventChannel();
}