package com.erzbir.numeron.core.utils;

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * @author Erzbir
 * @Date: 2023/5/2 01:10
 */
class CoroutineScopeBridge {
    companion object {
        fun cancel(coroutineScope: CoroutineScope) {
            coroutineScope.cancel()
        }

        fun cancel(coroutineScope: CoroutineScope, message: String) {
            coroutineScope.cancel(CancellationException(message))
        }

        fun cancel(coroutineScope: CoroutineScope, message: String, throwAble: Throwable) {
            coroutineScope.cancel(message, throwAble)
        }
    }
}
