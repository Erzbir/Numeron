package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * 实体权限过滤类
 */
public class WhitePermissionFilter extends AbstractPermissionFilter {
    @Override
    public Boolean filter(MessageEvent event, String text) {
        return GlobalConfig.whiteList.contains(event.getSender().getId());
    }

}
