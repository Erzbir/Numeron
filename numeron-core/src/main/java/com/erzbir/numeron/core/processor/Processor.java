package com.erzbir.numeron.core.processor;

/**
 * @author Erzbir
 * @Date: 2022/12/12 01:12
 * <p>实现这个接口的类用于初始化, 在程序最开始的时候运行, 用于处理注解或是实现特定接口的类</p>
 */
public interface Processor {
    void onApplicationEvent();
}
