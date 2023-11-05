package com.erzbir.numeron.core.bot;

import com.erzbir.numeron.api.bot.BotService;
import com.erzbir.numeron.api.context.DefaultAppContext;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.config.NumeronBotConfiguration;
import com.erzbir.numeron.core.context.MiraiListenerContext;
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
    public List<Bot> getRunningBotList() {
        return getBotList().stream().filter(t -> t.isOnline() && isEnable(t)).toList();
    }

    @Override
    public void shutdown(long qq) {
        shutdown(findBot(qq));
    }

    @Override
    public void shutdown(Bot bot) {
        getConfiguration(bot).disable();
        List<Bot> runningBotList = getRunningBotList();
        if (runningBotList.isEmpty()) {
            DefaultAppContext.INSTANCE.getProcessors().forEach(Processor::destroy);
            MiraiListenerContext.INSTANCE.cancelAll();
        }
    }

    @Override
    public void shutdownAll() {
        getRunningBotList().forEach(this::shutdown);
    }

    @Override
    public void launch(long qq) {
        launch(findBot(qq));
    }

    @Override
    public void launch(Bot bot) {
        getConfiguration(bot).enable();
        List<Bot> runningBotList = getRunningBotList();
        if (runningBotList.size() == 1) {
            DefaultAppContext.INSTANCE.getProcessors().forEach(Processor::onApplicationEvent);
        }
    }

    @Override
    public void launchAll() {
        getLoginBotList().forEach(this::launch);
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
        if (!getConfiguration(bot).isEnable() || bot.isOnline()) {
            return;
        }
        bot.login();
    }

    @Override
    public void login(long qq) {
        login(findBot(qq));
    }

    @Override
    public void loginAll() {
        getBotList().forEach(this::login);
    }

    @Override
    public void cancel(long qq) {
        cancel(findBot(qq));
    }

    @Override
    public void cancel(Bot bot) {
        bot.close();
    }

    @Override
    public void cancelAll() {
        getBotList().forEach(this::cancel);
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
