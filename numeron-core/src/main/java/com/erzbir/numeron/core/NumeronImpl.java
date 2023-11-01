package com.erzbir.numeron.core;

import com.erzbir.numeron.api.Numeron;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.config.NumeronConfiguration;
import com.erzbir.numeron.core.context.AppContext;
import net.mamoe.mirai.Bot;

import java.util.List;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/11 09:37
 */
public class NumeronImpl implements Numeron {
    @Override
    public String getWorkDir() {
        return NumeronConfiguration.INSTANCE.getWorkDir();
    }

    @Override
    public String getCacheDir() {
        return NumeronConfiguration.INSTANCE.getCacheDir();
    }

    @Override
    public String getPluginWorkDir() {
        return NumeronConfiguration.INSTANCE.getPluginWorkDir();
    }

    @Override
    public String getConfigDir() {
        return NumeronConfiguration.INSTANCE.getConfigDir();
    }

    @Override
    public Boolean getCache() {
        return NumeronConfiguration.INSTANCE.getCache();
    }

    @Override
    public List<Bot> getBots() {
        return NumeronConfiguration.INSTANCE.getBots();
    }

    @Override
    public void addProcessor(Processor processor) {
        AppContext.INSTANCE.addProcessor(processor);
    }

    @Override
    public void removeProcessor(Processor processor) {
        AppContext.INSTANCE.removeProcessor(processor);
    }

    @Override
    public Set<Processor> getProcessors() {
        return AppContext.INSTANCE.getProcessors();
    }

}
