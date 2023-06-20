package com.erzbir.numeron.console.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/16 09:46
 */
public interface HotJarLoad {
    List<Object> loadNewJar(File jar) throws IOException;
}
