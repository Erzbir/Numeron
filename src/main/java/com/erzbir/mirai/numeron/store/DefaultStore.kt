package com.erzbir.mirai.numeron.store

import net.jodah.expiringmap.ExpirationPolicy
import net.jodah.expiringmap.ExpiringMap
import net.mamoe.mirai.message.data.MessageChain
import java.util.concurrent.TimeUnit

object DefaultStore {
    private val data: ExpiringMap<Int, MessageChain> = ExpiringMap.builder()
        .maxSize(1000)
        .expiration(60 * 60 * 24, TimeUnit.SECONDS)
        .expirationPolicy(ExpirationPolicy.ACCESSED)
        .variableExpiration()
        .build()

    fun save(id: Int, message: MessageChain) {
        data[id] = message
    }

    fun find(id: Int): MessageChain? {
        return data[id]
    }
}