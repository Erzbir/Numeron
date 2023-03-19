package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.entity.BlackList;
import com.erzbir.numeron.core.entity.GroupList;
import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.filter.Filter;
import com.erzbir.numeron.core.filter.PluginChannelFilterInter;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMemberEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/17 09:43
 * <p>插件消息事件过滤类</p>
 * <p>没有完善, 只是个雏形</p>
 */
@Filter
@SuppressWarnings("unused")
public class PluginChannelFilter implements PluginChannelFilterInter {
    @Override
    public Boolean filter(BotEvent event) {
        if (event instanceof MessageEvent event1) {
            if (event1 instanceof GroupMessageEvent event2) {
                return NumeronBot.INSTANCE.isEnable()
                        && GroupList.INSTANCE.contains(event2.getGroup().getId())
                        && !BlackList.INSTANCE.contains(event2.getSender().getId());
            } else {
                return NumeronBot.INSTANCE.isEnable()
                        && !BlackList.INSTANCE.contains(((MessageEvent) event).getSender().getId());
            }
        } else if (event instanceof GroupMemberEvent event1) {
            return !BlackList.INSTANCE.contains(event1.getMember().getId());
        } else {
            return NumeronBot.INSTANCE.isEnable();
        }
    }
}
