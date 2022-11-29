package com.erzbir.mirai.numeron.plugins.game.sign.entity

import com.google.gson.Gson

// id: json
class User(
    var id: Long, // id
    var coins: Long, // 金币
    var meiLi: Long, // 魅力
    var tiLi: Long, // 体力
    var lastSignIn: Long,
    var alreadySignInDays: Long // 累签
) {
    companion object {
        fun fromJson(str: String?): User? {
            return if (str == null) null else Gson().fromJson(str, User::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}
