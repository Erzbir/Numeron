package com.erzbir.numeron.core.bot;

import com.erzbir.numeron.api.bot.BotService;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/5/3 12:21
 */
public class BotServiceImpl implements BotService {

    @NotNull
    @Override
    public Bot newBot(long qq, byte @NotNull [] passwordMD5, @NotNull BotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        if (bot != null) {
            return bot;
        }
        bot = BotFactory.INSTANCE.newBot(qq, passwordMD5, botConfiguration);
        return bot;
    }

    @NotNull
    @Override
    public Bot newBot(long qq, @NotNull String password, @NotNull BotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        if (bot != null) {
            return bot;
        }
        bot = BotFactory.INSTANCE.newBot(qq, password, botConfiguration);
        return bot;
    }

    @Override
    public Bot findBot(long qq) {
        return Bot.findInstance(qq);
    }

    @Override
    public List<Bot> getBotList() {
        return Bot.Companion.getInstances();
    }

    @Override
    public void login(Bot bot) {
        bot.login();
    }

    @NotNull
    @Override
    public Bot newBot(long qq, @NotNull BotAuthorization botAuthorization, @NotNull BotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        if (bot != null) {
            return bot;
        }
        bot = BotFactory.INSTANCE.newBot(qq, botAuthorization, botConfiguration);
        return bot;
    }
}
