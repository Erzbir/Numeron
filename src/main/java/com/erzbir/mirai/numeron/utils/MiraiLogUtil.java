package com.erzbir.mirai.numeron.utils;

import net.mamoe.mirai.utils.MiraiLogger;
import org.slf4j.Logger;

/**
 * @author Erzbir
 * @Date: 2022/11/30 12:46
 * <p>日志输出工具类</p>
 */
public class MiraiLogUtil {
    private static final MiraiLogger logger = MiraiLogger.Factory.INSTANCE.create(Logger.class);

    private MiraiLogUtil() {

    }

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
