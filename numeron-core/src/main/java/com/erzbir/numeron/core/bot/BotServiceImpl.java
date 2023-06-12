package com.erzbir.numeron.core.bot;

import com.erzbir.numeron.api.bot.BotService;
import com.erzbir.numeron.api.bot.NumeronBotConfiguration;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.context.ListenerContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/5/3 12:21
 */
public class BotServiceImpl implements BotService {

    @NotNull
    public Bot newBot(long qq, @NotNull byte[] passwordMD5, @NotNull NumeronBotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        return bot == null ? BotFactory.INSTANCE.newBot(qq, passwordMD5, botConfiguration) : bot;
    }

    @NotNull
    public Bot newBot(long qq, @NotNull String password, @NotNull NumeronBotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        return bot == null ? BotFactory.INSTANCE.newBot(qq, password, botConfiguration) : bot;
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
    public void shutdown(long qq) {
        AppContext.INSTANCE.getProcessors().forEach(Processor::destroy);
        ListenerContext.INSTANCE.cancelAll();
        getConfiguration(qq).disable();
    }

    @Override
    public void launch(long qq) {
        AppContext.INSTANCE.getProcessors().forEach(Processor::onApplicationEvent);
    }

    @Override
    public NumeronBotConfiguration getConfiguration(long qq) {
        Bot bot = findBot(qq);
        return bot == null ? null : (NumeronBotConfiguration) (bot.getConfiguration());
    }

    @Override
    public void login(Bot bot) {
        bot.login();
    }

    @NotNull
    public Bot newBot(long qq, @NotNull BotAuthorization botAuthorization, @NotNull NumeronBotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        return bot == null ? BotFactory.INSTANCE.newBot(qq, botAuthorization, botConfiguration) : bot;
    }
}
