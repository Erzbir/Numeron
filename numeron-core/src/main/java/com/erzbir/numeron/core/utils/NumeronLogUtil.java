package com.erzbir.numeron.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Erzbir
 * @Date: 2022/11/30 12:46
 * <p>日志输出工具类</p>
 */
public class NumeronLogUtil {
    public static final Logger logger = LogManager.getLogger("Numeron");
    ;

    private NumeronLogUtil() {

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
        logger.warn(s);
    }

    public static void trace(String s) {
        logger.trace(s);
    }
}
