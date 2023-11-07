package com.erzbir.numeron;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.boot.StarterController;

public class NumeronBotApplication {

    public static void main(String[] args) {
        StarterController starterController = new StarterController();
        starterController.boot(NumeronBotApplication.class, NumeronBotApplication.class.getClassLoader());  // 调用 boot 方法初始化
        BotServiceImpl.INSTANCE.loginAll();
    }
}

/*
 | \ | |_   _ _ __ ___   ___ _ __ ___  _ __
 |  \| | | | | '_ ` _ \ / _ \ '__/ _ \| '_ \
 | |\  | |_| | | | | | |  __/ | | (_) | | | |
 |_| \_|\__,_|_| |_| |_|\___|_|  \___/|_| |_|
 */
