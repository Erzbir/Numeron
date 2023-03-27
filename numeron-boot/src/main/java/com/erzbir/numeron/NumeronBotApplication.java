package com.erzbir.numeron;

import com.erzbir.numeron.boot.Starter;
import com.erzbir.numeron.core.entity.NumeronBot;

public class NumeronBotApplication {
    private static final String packageName = "com.erzbir.numeron";

    static {
        NumeronBot numeronBot = NumeronBot.INSTANCE;  // 这里是为了提前初始化, 不然会出现空指针异常
    }

    public static void main(String[] args) {
        Starter starter = new Starter(packageName, NumeronBotApplication.class.getClassLoader());
        starter.boot();  // 调用boot方法启动
        NumeronBot.INSTANCE.login();
        System.out.println("""
                | \\ | |_   _ _ __ ___   ___ _ __ ___  _ __ \s
                |  \\| | | | | '_ ` _ \\ / _ \\ '__/ _ \\| '_ \\\s
                | |\\  | |_| | | | | | |  __/ | | (_) | | | |
                |_| \\_|\\__,_|_| |_| |_|\\___|_|  \\___/|_| |_|""".indent(1));
        System.out.println("欢迎使用Numeron!!!");
    }
}

/*
 | \ | |_   _ _ __ ___   ___ _ __ ___  _ __
 |  \| | | | | '_ ` _ \ / _ \ '__/ _ \| '_ \
 | |\  | |_| | | | | | |  __/ | | (_) | | | |
 |_| \_|\__,_|_| |_| |_|\___|_|  \___/|_| |_|
 */
