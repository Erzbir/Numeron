package com.erzbir.numeron.annotation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author Erzbir
 * @Data 2023/7/26
 * <p>
 *     一个默认的协程作用域
 * </p>
 */
class DefaultScope : CoroutineScope {
    private val job = Job()


    override val coroutineContext: CoroutineContext = Dispatchers.Default + job

    fun cancelJob() {
        job.cancel()
    }

    companion object {
        @JvmField
        val INSTANCE = DefaultScope()
    }
}