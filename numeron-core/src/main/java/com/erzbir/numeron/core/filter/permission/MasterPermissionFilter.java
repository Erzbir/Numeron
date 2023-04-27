package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.entity.NumeronBot;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>主人权限过滤类, 过滤掉(舍弃)不是主人的event</p>
 */
public class MasterPermissionFilter extends AbstractPermissionFilter {
    public static final MasterPermissionFilter INSTANCE = new MasterPermissionFilter();

    private MasterPermissionFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event) {
        return NumeronBot.INSTANCE.getMaster() == event.getSender().getId();
    }
}
