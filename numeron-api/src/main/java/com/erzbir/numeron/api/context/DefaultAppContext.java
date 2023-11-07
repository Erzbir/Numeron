package com.erzbir.numeron.api.context;

import com.erzbir.numeron.api.processor.Processor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 程序预处理器和一些环境属性放入这个类中
 * @author Erzbir
 * @Date 2023/11/2
 */
public class DefaultAppContext implements AppContext {
    public final static DefaultAppContext INSTANCE = new DefaultAppContext();
    private final Set<Processor> processors = new HashSet<>();
    private final Map<Object, Object> attributes = new HashMap<>();

    @Override
    public void addProcessor(Processor processor) {
        processors.add(processor);
    }

    @Override
    public void removeProcessor(Processor processor) {
        processors.remove(processor);
    }

    @Override
    public Set<Processor> getProcessors() {
        return processors;
    }

    @Override
    public void setAttribute(Object key, Object value) {
        attributes.put(key, value);
    }

    @Override
    public Object getAttribute(Object key) {
        return attributes.get(key);
    }

    @Override
    public boolean contains(Object object) {
        return processors.contains(object);
    }
}
