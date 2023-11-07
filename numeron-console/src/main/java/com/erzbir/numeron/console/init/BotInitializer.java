package com.erzbir.numeron.console.init;

import com.erzbir.numeron.boot.Initializer;
import com.erzbir.numeron.console.bot.BotLoader;

/**
 * @author Erzbir
 * @Date 2023/11/3
 */
public class BotInitializer implements Initializer {

    @Override
    public void init() {
        BotLoader.load();
    }
}
