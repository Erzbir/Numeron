package com.erzbir.numeron;

import com.erzbir.numeron.core.boot.Starter;

public class NumeronBotApplication {

    public static void main(String[] args) throws InterruptedException {
        Starter starter = new Starter(NumeronBotApplication.class.getClassLoader());
        starter.boot(Starter.BotType.SPI);  // 调用 boot 方法初始化
    }
}

/*
 | \ | |_   _ _ __ ___   ___ _ __ ___  _ __
 |  \| | | | | '_ ` _ \ / _ \ '__/ _ \| '_ \
 | |\  | |_| | | | | | |  __/ | | (_) | | | |
 |_| \_|\__,_|_| |_| |_|\___|_|  \___/|_| |_|
 */
