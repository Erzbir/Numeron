package com.erzbir.mirai.numeron;

import com.erzbir.mirai.numeron.configs.BotConfig;
import com.erzbir.mirai.numeron.processor.MessageAnnotationProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumeronBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotConfig.class, args);
        MessageAnnotationProcessor.bot.login();
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
