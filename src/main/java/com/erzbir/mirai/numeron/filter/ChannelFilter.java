package com.erzbir.mirai.numeron.filter;

import com.erzbir.mirai.numeron.Annotation.Filter;
import com.erzbir.mirai.numeron.Interface.ChannelFilterInter;
import com.erzbir.mirai.numeron.configs.GlobalConfig;
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
        return GlobalConfig.isOn
                && !GlobalConfig.blackList.contains(((MessageEvent) event).getSender().getId())
                && GlobalConfig.groupList.contains(((GroupMessageEvent) event).getGroup().getId());
    }
}
