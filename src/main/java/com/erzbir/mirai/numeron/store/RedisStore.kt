package com.erzbir.mirai.numeron.store

import redis.clients.jedis.Jedis

object RedisStore {
    private val client = Jedis("localhost", 6379)

    fun set(key: String, value: String, seconds: Long = -1L) {
        if (seconds == -1L) {
            client.set(key, value)
        } else {
            client.setex(key, seconds, value)
        }
    }

    fun del(key: String) {
        println("del:$key")
        client.del(key)
    }

    fun get(key: String): String? {
        return client.get(key)
    }

    fun getPic(): List<String> {
        return client.keys("pic_*").map {
            client.get(it)
        }
    }

    fun getFile(): List<String> {
        return client.keys("file_*").map {
            client.get(it)
        }
    }

    fun getPlain(): List<String> {
        return client.keys("plain_*").map {
            client.get(it)
        }
    }

    fun removePic(filename: String) {
        client.keys("pic_*").filter {
            client.get(it) == filename
        }.forEach {
            client.del(it)
        }
    }

    fun removeFile(filename: String) {
        client.keys("file_*").filter {
            client.get(it) == filename
        }.forEach {
            client.del(it)
        }
    }

    fun removePlain(filename: String) {
        client.keys("plain_*").filter {
            client.get(it) == filename
        }.forEach {
            client.del(it)
        }
    }

    fun useClient(r: (client: Jedis) -> Unit) {
        r(client)
    }
}