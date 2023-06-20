package com.erzbir.numeron.api.bot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.auth.BotAuthorization;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/5/2 21:56
 */
public interface BotService {
    Bot findBot(long qq);

    List<Bot> getBotList();

    List<Bot> getRunningBotList();

    void shutdown(long qq);

    void shutdown(Bot bot);

    void shutdownAll();

    void launch(long qq);

    void launch(Bot bot);

    void launchAll();

    void login(Bot bot);

    void login(long qq);

    void loginAll();

    void cancel(long qq);

    void cancel(Bot bot);

    void cancelAll();

    List<Bot> getLoginBotList();

    boolean isEnable(long qq);

    boolean isEnable(Bot bot);

    long getMaster(long qq);

    long getMaster(Bot bot);

    NumeronBotConfiguration getConfiguration(long qq);

    NumeronBotConfiguration getConfiguration(Bot bot);

    @NotNull
    Bot newBot(long qq, @NotNull byte[] passwordMD5, @NotNull NumeronBotConfiguration botConfiguration);

    @NotNull
    Bot newBot(long qq, @NotNull String password, @NotNull NumeronBotConfiguration botConfiguration);

    @NotNull
    Bot newBot(long qq, BotAuthorization botAuthorization, @NotNull NumeronBotConfiguration botConfiguration);

}
