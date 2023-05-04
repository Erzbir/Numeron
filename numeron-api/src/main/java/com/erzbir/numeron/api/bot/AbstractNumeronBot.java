package com.erzbir.numeron.api.bot;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2023/5/2 01:25
 */
public abstract class AbstractNumeronBot implements NumeronBotInter, CoroutineScope {
    protected NumeronBotConfiguration configuration;

    @NotNull
    @Override
    public CoroutineContext getCoroutineContext() {
        return EmptyCoroutineContext.INSTANCE;
    }
}
