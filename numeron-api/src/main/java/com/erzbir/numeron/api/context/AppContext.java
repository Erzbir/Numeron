package com.erzbir.numeron.api.context;

import com.erzbir.numeron.api.processor.Processor;

import java.util.Set;

/**
 * @author Erzbir
 * @Date 2023/11/2
 */
public interface AppContext extends Context {
    void addProcessor(Processor processor);

    void removeProcessor(Processor processor);

    Set<Processor> getProcessors();

    void setAttribute(Object key, Object value);

    Object getAttribute(Object key);

}
