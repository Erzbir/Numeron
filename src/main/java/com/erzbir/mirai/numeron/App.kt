package com.erzbir.mirai.numeron

import com.erzbir.mirai.numeron.configs.BotConfig
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main(args: Array<String>) {
    AnnotationConfigApplicationContext(BotConfig::class.java)
}