package com.erzbir.numeron.api.bot;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Erzbir
 * @Date: 2023/5/2 01:53
 */
class AbstractNumeronBotTest {

    @Test
    void getCoroutineContext() {
        AbstractNumeronBot abstractNumeronBot = new AbstractNumeronBot() {
            @Override
            public long getMaster() {
                return 0;
            }

            @Override
            public boolean isEnable() {
                return false;
            }

            @Override
            public String getWorkDir() {
                return null;
            }

            @Override
            public void shutdown() {

            }

            @Override
            public void launch() {

            }

            @Override
            public Bot getBot() {
                return null;
            }

            @Override
            public EventChannel<BotEvent> getEventChannel() {
                return null;
            }
        };
        CoroutineContext coroutineContext = abstractNumeronBot.getCoroutineContext();
        System.out.println(coroutineContext);
    }
}