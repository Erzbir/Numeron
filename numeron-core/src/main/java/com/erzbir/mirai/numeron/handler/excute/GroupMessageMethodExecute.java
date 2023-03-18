package com.erzbir.mirai.numeron.handler.excute;

import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.lang.reflect.Method;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:30
 * <P>群消息处理方法</P>
 */
public class GroupMessageMethodExecute implements MethodExecute, RegisterEventHandle {
    public static final GroupMessageMethodExecute INSTANCE = new GroupMessageMethodExecute();

    private GroupMessageMethodExecute() {

    }

    @Override
    public void execute(Method method, Object bean, EventChannel<BotEvent> channel) {
        register(channel, GroupMessageEvent.class, method, bean);
    }
}
