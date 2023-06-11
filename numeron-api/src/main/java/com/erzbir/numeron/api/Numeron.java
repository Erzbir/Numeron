package com.erzbir.numeron.api;

import com.erzbir.numeron.api.processor.Processor;

import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/6/11 09:20
 */
public interface Numeron {
    String getWorkDir();

    String getCacheDir();

    String getPluginWorkDir();

    Boolean getCache();

    void addProcessor(Processor processor);

    void removeProcessor(Processor processor);

    Set<Processor> getProcessors();

    void setRegisterBefore(Runnable runnable);

    void setRegisterAfter(Runnable runnable);

    void setInvokeBefore(Runnable runnable);

    void setInvokeAfter(Runnable runnable);
}
