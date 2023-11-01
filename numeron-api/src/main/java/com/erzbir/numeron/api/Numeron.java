package com.erzbir.numeron.api;

import com.erzbir.numeron.api.processor.Processor;
import net.mamoe.mirai.Bot;

import java.util.List;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/11 09:20
 */
public interface Numeron {
    String getWorkDir();

    String getCacheDir();

    String getPluginWorkDir();

    String getConfigDir();

    Boolean getCache();

    List<Bot> getBots();

    void addProcessor(Processor processor);

    void removeProcessor(Processor processor);

    Set<Processor> getProcessors();
}
