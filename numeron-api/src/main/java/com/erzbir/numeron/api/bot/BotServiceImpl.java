package com.erzbir.numeron.api.bot;

import java.util.ServiceLoader;

/**
 * @author Erzbir
 * @Date: 2023/5/3 12:38
 */
public class BotServiceImpl {
    public static BotService INSTANCE = ServiceLoader.load(BotService.class).findFirst().get();
}
