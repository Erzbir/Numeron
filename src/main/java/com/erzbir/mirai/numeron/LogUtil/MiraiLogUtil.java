package com.erzbir.mirai.numeron.LogUtil;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.utils.MiraiLogger;

/**
 * @author Erzbir
 * @Date: 2022/11/30 12:46
 */
@Slf4j
public class MiraiLogUtil {
    private static final MiraiLogger logger = MiraiLogger.Factory.INSTANCE.create(log.getClass());

    public static void info(String s) {
        logger.info(s);
    }

    public static void err(String s) {
        logger.error(s);
    }

    public static void debug(String s) {
        logger.debug(s);
    }

    public static void warning(String s) {
        logger.warning(s);
    }

    public static void verbose(String s) {
        logger.verbose(s);
    }
}
