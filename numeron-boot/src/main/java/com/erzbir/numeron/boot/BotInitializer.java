package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.bot.BotServiceImpl;

import java.util.concurrent.CompletableFuture;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
public class BotInitializer implements Initializer {

    @Override
    public void init() {
        CompletableFuture.runAsync(() -> BotServiceImpl.INSTANCE.getBotList().forEach(t -> BotServiceImpl.INSTANCE.login(t)));
    }
}
