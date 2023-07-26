package com.erzbir.numeron.console.plugin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * @author Erzbir
 * @Data 2023/7/26
 */
open class NumeronPluginKt : Plugin {
    private lateinit var description: NumeronDescription
    private var enable = false
    private val job = Job()

    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    protected fun NumeronPlugin(description: NumeronDescription) {
        this.description = description
    }

    override fun onEnable() {
    }

    override fun onDisable() {
    }

    override fun onLoad() {
    }

    override fun onUnLoad() {
    }

    override fun enable() {
        enable = true
    }

    override fun disable() {
        enable = false
    }

    override fun isEnable(): Boolean {
        return enable
    }

    override fun getDescription(): NumeronDescription {
        return description
    }
}