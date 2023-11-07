package com.erzbir.numeron.api;

import com.erzbir.numeron.api.context.DefaultAppContext;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.config.NumeronConfiguration;
import net.mamoe.mirai.Bot;

import java.util.List;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/11 09:37
 */
public class NumeronImpl implements Numeron {
    public final static NumeronImpl INSTANCE = new NumeronImpl();

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
        DefaultAppContext.INSTANCE.addProcessor(processor);
    }

    @Override
    public void removeProcessor(Processor processor) {
        DefaultAppContext.INSTANCE.removeProcessor(processor);
    }

    @Override
    public Set<Processor> getProcessors() {
        return DefaultAppContext.INSTANCE.getProcessors();
    }

}
