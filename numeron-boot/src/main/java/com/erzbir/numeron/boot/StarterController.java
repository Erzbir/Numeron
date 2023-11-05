package com.erzbir.numeron.boot;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.Bot;

import java.util.ArrayList;
import java.util.List;

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
    private Starter starter;
    private List<Initializer> initializers = new ArrayList<>();

    public StarterController() {
        initializers.add(new ConfigInitializer());
        initializers.add(new PluginInitializer());
        initializers.add(new BotInitializer());
    }


    public void boot(Class<?> bootClass, ClassLoader classLoader) throws InterruptedException {
        initializers.forEach(Initializer::init);
        setStarter(new SpiStarter(bootClass.getName(), classLoader));
        starter.boot();
        printLog();
        NumeronLogUtil.info("欢迎使用 Numeron!!!");
        BotServiceImpl.INSTANCE.getBotList().forEach(Bot::join);
    }


    private void printLog() {
        NumeronLogUtil.info(StarterController.LOGO.indent(1));
    }

}
