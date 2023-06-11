package com.erzbir.numeron.api.bot;

import net.mamoe.mirai.Bot;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/5/2 21:56
 */
public interface BotService {
    Bot findBot(long qq);

    List<Bot> getBotList();

    void shutdown(long qq);

    void launch(long qq);

    void login(Bot bot);

    NumeronBotConfiguration getConfiguration(long qq);
}
