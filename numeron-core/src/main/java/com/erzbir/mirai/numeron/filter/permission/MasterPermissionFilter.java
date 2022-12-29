package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * 实体权限过滤类
 */
public class MasterPermissionFilter extends AbstractPermissionFilter {
    public static final MasterPermissionFilter INSTANCE = new MasterPermissionFilter();

    private MasterPermissionFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return NumeronBot.INSTANCE.getMaster() == event.getSender().getId();
    }
}
