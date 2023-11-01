package com.erzbir.numeron;

import com.erzbir.numeron.boot.SpiStarter;
import com.erzbir.numeron.boot.Starter;
import com.erzbir.numeron.boot.StarterController;

public class NumeronBotApplication {

    public static void main(String[] args) throws InterruptedException {
        Starter starter = new SpiStarter(NumeronBotApplication.class, NumeronBotApplication.class.getClassLoader());
        StarterController starterController = new StarterController();
        starterController.setStarter(starter);
        starterController.boot();  // 调用 boot 方法初始化
    }
}

/*
 | \ | |_   _ _ __ ___   ___ _ __ ___  _ __
 |  \| | | | | '_ ` _ \ / _ \ '__/ _ \| '_ \
 | |\  | |_| | | | | | |  __/ | | (_) | | | |
 |_| \_|\__,_|_| |_| |_|\___|_|  \___/|_| |_|
 */
