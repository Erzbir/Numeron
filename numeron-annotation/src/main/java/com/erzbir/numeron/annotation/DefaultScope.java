package com.erzbir.numeron.annotation;

import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Data 2023/7/24
 */
public class DefaultScope implements CoroutineScope {

    @NotNull
    @Override
    public CoroutineContext getCoroutineContext() {
        return EmptyCoroutineContext.INSTANCE;
    }
}
