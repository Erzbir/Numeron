package com.erzbir.numeron.console.plugin;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import net.mamoe.mirai.event.ListenerHost;
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
        return EmptyCoroutineContext.INSTANCE;
    }

}
