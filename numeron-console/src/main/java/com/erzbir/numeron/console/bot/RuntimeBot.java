package com.erzbir.numeron.console.bot;

import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.ConcurrencyKind;
import net.mamoe.mirai.event.EventPriority;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2023/5/4 01:05
 */
public class RuntimeBot {
    private static Bot bot;

    static {
        GlobalEventChannel.INSTANCE.subscribeAlways(BotEvent.class, EmptyCoroutineContext.INSTANCE, ConcurrencyKind.LOCKED, EventPriority.HIGH, botEvent -> bot = botEvent.getBot());
    }


    public static Bot getRuntimeBot() {
        return bot;
    }
}
