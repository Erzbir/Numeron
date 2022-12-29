package com.erzbir.mirai.numeron.bot.game.sign.entity;

import com.google.gson.Gson;

/**
 * @author Erzbir
 * @Date: 2022/12/1 01:03
 */
public class User {
    public final long id;
    public long coins;
    public long meiLi;
    public long tiLi;
    public long lastSignIn;
    public long alreadySignInDays;

    public User(long id, long coins, long meiLi, long tiLi, long lastSignIn, long alreadySignInDays) {
        this.id = id;
        this.coins = coins;
        this.meiLi = meiLi;
        this.tiLi = tiLi;
        this.lastSignIn = lastSignIn;
        this.alreadySignInDays = alreadySignInDays;
    }

    public static String toJson(User user) {
        return new Gson().toJson(user);
    }

    public static User fromJson(String str) {
        return str == null ? null : (new Gson()).fromJson(str, User.class);
    }
}
