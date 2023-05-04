package com.erzbir.numeron.api.bot;

import kotlin.coroutines.CoroutineContext;
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
            public void shutdown() {

            }

            @Override
            public void launch() {

            }
        };
        CoroutineContext coroutineContext = abstractNumeronBot.getCoroutineContext();
        System.out.println(coroutineContext);
    }
}