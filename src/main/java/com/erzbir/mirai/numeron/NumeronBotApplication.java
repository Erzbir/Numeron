package com.erzbir.mirai.numeron;

import com.erzbir.mirai.numeron.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NumeronBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotConfig.class, args);
    }

}
