package com.erzbir.mirai.numeron.filter;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/17 09:43
 */
@Filter
public class PluginChannelFilter implements ChannelFilterInter {

    @Override
    public Boolean filter(MessageEvent event, String text) {
        if (event instanceof GroupMessageEvent) {
            return GlobalConfig.isOn
                    && GlobalConfig.groupList.contains(((GroupMessageEvent) event).getGroup().getId())
                    && !GlobalConfig.blackList.contains(((GroupMessageEvent) event).getSender().getId());
        } else {
            return GlobalConfig.isOn
                    && !GlobalConfig.blackList.contains(event.getSender().getId());
        }
    }
}
