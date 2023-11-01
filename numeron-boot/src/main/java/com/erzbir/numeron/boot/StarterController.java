package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.Bot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Erzbir
 * @Date: 2022/12/12 22:45
 */
@Getter
@Setter
public class StarterController {
    private static final String LOGO = """
                        
            | \\ | |_   _ _ __ ___   ___ _ __ ___  _ __ \s
            |  \\| | | | | '_ ` _ \\ / _ \\ '__/ _ \\| '_ \\\s
            | |\\  | |_| | | | | | |  __/ | | (_) | | | |
            |_| \\_|\\__,_|_| |_| |_|\\___|_|  \\___/|_| |_|
                        
            """;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Starter.Initializer initial = new Starter.Initializer();
    private Starter starter;

    public void boot() throws InterruptedException {
        long l = System.currentTimeMillis();
        initial.initConfig();
        initial.initPlugin();
        starter.boot();
        initial.initLogin();
        while (!initial.getExecutor().isTerminated()) {
            Thread.sleep(100);
        }
        printLog();
        NumeronLogUtil.info("欢迎使用 Numeron!!!");
        BotServiceImpl.INSTANCE.getBotList().forEach(Bot::join);
    }


    private void printLog() {
        NumeronLogUtil.info(StarterController.LOGO.indent(1));
    }

}
