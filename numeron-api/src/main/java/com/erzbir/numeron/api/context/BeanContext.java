package com.erzbir.numeron.api.context;

/**
 * @author Erzbir
 * @Date 2023/11/2
 */
public interface BeanContext extends BeanFactory {
    void addBean(Object bean);

    void addBean(String name, Object bean);

    Object remove(Object bean);

    Object remove(String name);
}
