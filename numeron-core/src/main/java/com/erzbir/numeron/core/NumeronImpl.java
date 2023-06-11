package com.erzbir.numeron.core;

import com.erzbir.numeron.api.Numeron;
import com.erzbir.numeron.api.config.NumeronConfiguration;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.context.AppContext;
import com.erzbir.numeron.core.context.ListenerContext;
import com.erzbir.numeron.core.handler.excute.EventMethodExecute;

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
    public Boolean getCache() {
        return NumeronConfiguration.INSTANCE.getCache();
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

    @Override
    public void setRegisterBefore(Runnable runnable) {
        ListenerContext.INSTANCE.getListenerRegister().setRunBefore(runnable);
    }

    @Override
    public void setRegisterAfter(Runnable runnable) {
        ListenerContext.INSTANCE.getListenerRegister().setRunAfter(runnable);
    }

    @Override
    public void setInvokeBefore(Runnable runnable) {
        EventMethodExecute.INSTANCE.executeBefore(runnable);
    }

    @Override
    public void setInvokeAfter(Runnable runnable) {
        EventMethodExecute.INSTANCE.executeAfter(runnable);
    }
}
