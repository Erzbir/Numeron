package com.erzbir.numeron.console.plugin;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2023/4/26 14:07
 */
public abstract class NumeronPlugin implements Plugin {
    private final NumeronDescription description;
    private boolean enable = false;

    protected NumeronPlugin(NumeronDescription description) {
        this.description = description;
    }

    @Override
    public NumeronDescription getDescription() {
        return description;
    }

    @Override
    public void enable() {
        enable = true;
        onEnable();
    }

    @Override
    public void disable() {
        enable = false;
        onDisable();
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

    @NotNull
    @Override
    public CoroutineContext getCoroutineContext() {
        Bot bot = BotFactory.INSTANCE.newBot(10000000012L, "oindowdawd", new BotConfiguration());
        return bot.getCoroutineContext();
    }

}
