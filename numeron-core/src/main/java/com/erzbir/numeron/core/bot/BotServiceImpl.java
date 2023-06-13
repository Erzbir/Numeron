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

import java.util.LinkedList;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/5/3 12:21
 */
public class BotServiceImpl implements BotService {
    private static final List<Bot> runningBots = new LinkedList<>();

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

    @NotNull
    public Bot newBot(long qq, @NotNull BotAuthorization botAuthorization, @NotNull NumeronBotConfiguration botConfiguration) {
        Bot bot = findBot(qq);
        return bot == null ? BotFactory.INSTANCE.newBot(qq, botAuthorization, botConfiguration) : bot;
    }

    @Override
    public Bot findBot(long qq) {
        return Bot.findInstance(qq);
    }

    @Override
    public List<Bot> getBotList() {
        return Bot.getInstances();
    }

    @Override
    public void shutdown(long qq) {
        getConfiguration(qq).disable();
        runningBots.remove(findBot(qq));
        if (runningBots.size() == 0) {
            AppContext.INSTANCE.getProcessors().forEach(Processor::destroy);
            ListenerContext.INSTANCE.cancelAll();
        }
    }

    @Override
    public void launch(long qq) {
        getConfiguration(qq).enable();
        runningBots.add(findBot(qq));
        if (runningBots.size() == 1) {
            AppContext.INSTANCE.getProcessors().forEach(Processor::onApplicationEvent);
        }
    }

    @Override
    public NumeronBotConfiguration getConfiguration(long qq) {
        Bot bot = findBot(qq);
        return bot == null ? null : (NumeronBotConfiguration) (bot.getConfiguration());
    }

    @Override
    public NumeronBotConfiguration getConfiguration(Bot bot) {
        return (NumeronBotConfiguration) bot.getConfiguration();
    }

    @Override
    public void login(Bot bot) {
        if (!getConfiguration(bot).isEnable()) {
            return;
        }
        bot.login();
        runningBots.add(bot);
    }

    @Override
    public void login(long qq) {
        Bot bot = findBot(qq);
        bot.login();
        runningBots.add(bot);
    }

    @Override
    public List<Bot> getLoginBotList() {
        List<Bot> botList = getBotList();
        return botList.stream().filter(Bot::isOnline).toList();
    }

    @Override
    public boolean isEnable(long qq) {
        return getConfiguration(qq).isEnable();
    }

    @Override
    public boolean isEnable(Bot bot) {
        return getConfiguration(bot).isEnable();
    }

    @Override
    public long getMaster(long qq) {
        return getConfiguration(qq).getMaster();
    }

    @Override
    public long getMaster(Bot bot) {
        return getConfiguration(bot).getMaster();
    }

}
