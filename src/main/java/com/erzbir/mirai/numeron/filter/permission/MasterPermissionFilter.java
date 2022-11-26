package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * 实体权限过滤类
 */
public class MasterPermissionFilter extends AbstractPermissionFilter {

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return event.getSender().getId() == GlobalConfig.master;
    }
}
