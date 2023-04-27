package com.erzbir.numeron.core.proxy;

import com.erzbir.numeron.core.entity.NumeronBot;
import net.mamoe.mirai.event.events.MessageEvent;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2023/4/26 09:58
 */
class MiraiEventChannelProxyTest {
    @Test
    public void test() {
        MiraiEventChannelProxy miraiEventChannelProxy = new MiraiEventChannelProxy(new HashMap<>());
        miraiEventChannelProxy.setInvokeAfter(() -> System.out.println("调用之后"));
        miraiEventChannelProxy.setInvokeBefore(() -> System.out.println("调用之前"));
        MiraiEventChannelProxy.EventChannelMethodInvokeInter methodInvoker = miraiEventChannelProxy.getProxy();
        methodInvoker.subscribeAlways(NumeronBot.INSTANCE.getBot().getEventChannel(), MessageEvent.class, messageEvent -> {
        });
    }

}