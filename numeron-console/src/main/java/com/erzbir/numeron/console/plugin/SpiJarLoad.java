package com.erzbir.numeron.console.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/16 10:05
 */
public interface SpiJarLoad {
    List<Object> loadJarFromSpi(File file, Class<?> type) throws IOException;
}
