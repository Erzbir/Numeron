package com.erzbir.mirai.numeron.filter;

import com.erzbir.mirai.numeron.annotation.filter.Filter;
import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.interfaces.ChannelFilterInter;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/17 09:43
 */
@Filter
public class ChannelFilter implements ChannelFilterInter {
    @Override
    public Boolean filter(BotEvent event) {
        if (event instanceof GroupMessageEvent) {
            return GlobalConfig.isOn
                    && GlobalConfig.groupList.contains(((GroupMessageEvent) event).getGroup().getId())
                    && !GlobalConfig.blackList.contains(((GroupMessageEvent) event).getSender().getId());
        } else {
            return GlobalConfig.isOn
                    && !GlobalConfig.blackList.contains(((MessageEvent) event).getSender().getId());
        }
    }
}
