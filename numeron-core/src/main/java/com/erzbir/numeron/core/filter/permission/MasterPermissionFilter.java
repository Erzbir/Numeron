package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.api.bot.BotServiceImpl;
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
        return BotServiceImpl.INSTANCE.getMaster(event.getBot()) == event.getSender().getId();
    }
}
