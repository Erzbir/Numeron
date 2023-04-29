package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.api.entity.WhiteService;
import com.erzbir.numeron.core.entity.NumeronBot;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>白名单权限过滤类, 过滤掉(舍弃)不是白名单的event</p>
 */
public class WhitePermissionFilter extends AbstractPermissionFilter {
    public static final WhitePermissionFilter INSTANCE = new WhitePermissionFilter();

    private WhitePermissionFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event) {
        long id = event.getSender().getId();
        return id == NumeronBot.INSTANCE.getMaster() || WhiteService.INSTANCE.exist(id);
    }

}
