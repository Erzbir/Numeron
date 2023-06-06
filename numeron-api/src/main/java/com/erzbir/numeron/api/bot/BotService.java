package com.erzbir.numeron.api.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/5/2 21:56
 */
public interface BotService extends BotFactory, BotLogin {
    Bot findBot(long qq);

    List<Bot> getBotList();
}
