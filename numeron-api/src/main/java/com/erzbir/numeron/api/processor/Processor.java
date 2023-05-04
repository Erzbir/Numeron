package com.erzbir.numeron.api.processor;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date: 2022/12/12 01:12
 * <p>实现这个接口的类用于初始化, 在程序最开始的时候运行, 可以用于拓展功能, 比如用于处理注解或一些方法在程序初始化时调用</p>
 */
public interface Processor {
    void onApplicationEvent();
}
