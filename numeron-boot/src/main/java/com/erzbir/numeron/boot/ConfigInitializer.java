package com.erzbir.numeron.boot;

import com.erzbir.numeron.console.bot.BotLoader;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
public class ConfigInitializer implements Initializer {
    @Override
    public void init() {
        BotLoader.load();
    }
}
