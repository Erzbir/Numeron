package com.erzbir.numeron.core.processor;

import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.api.context.BeanContext;
import com.erzbir.numeron.api.context.DefaultBeanCentral;
import com.erzbir.numeron.api.processor.Processor;
import com.erzbir.numeron.core.listener.EnhanceListenerRegister;
import com.erzbir.numeron.utils.NumeronLogUtil;
import kotlin.coroutines.EmptyCoroutineContext;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.GlobalEventChannel;

/**
 * <p>
 * 从 bean 容器中获取有特定注解的 bean, 并根据方法上的注解过滤 channel 后注册事件监听
 * </p>
 *
 * @author Erzbir
 * @Date: 2022/11/18 15:10
 * @see Processor
 */
@Setter
@Getter
public class ListenerRegisterProcessor implements Processor {
    public EventChannel<? extends net.mamoe.mirai.event.Event> channel;
    private EnhanceListenerRegister enhanceListenerRegister = new EnhanceListenerRegister();
    private BeanContext context = DefaultBeanCentral.INSTANCE;


    @Override
    public void onApplicationEvent() {
        this.channel = GlobalEventChannel.INSTANCE;
        NumeronLogUtil.info("开始注册注解消息处理监听......");
        context.getBeansWithAnnotation(Listener.class).values().forEach(v -> enhanceListenerRegister.registerListenerHost(channel, v, EmptyCoroutineContext.INSTANCE)
        );
        NumeronLogUtil.info("注解消息处理监听注册完毕\n");
    }

    @Override
    public void destroy() {

    }
}