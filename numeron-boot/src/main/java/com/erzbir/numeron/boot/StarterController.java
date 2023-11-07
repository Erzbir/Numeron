package com.erzbir.numeron.boot;

import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ServiceLoader;

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


    public void boot(Class<?> bootClass, ClassLoader classLoader) {
        boot(bootClass.getPackageName(), classLoader);
    }

    public void boot(String name, ClassLoader classLoader) {
        ServiceLoader.load(Initializer.class).forEach(Initializer::init);
        setStarter(new SpiStarter(name, classLoader));
        starter.boot();
        printLog();
        NumeronLogUtil.info("欢迎使用 Numeron!!!");
    }


    private void printLog() {
        NumeronLogUtil.info(StarterController.LOGO.indent(1));
    }

}
