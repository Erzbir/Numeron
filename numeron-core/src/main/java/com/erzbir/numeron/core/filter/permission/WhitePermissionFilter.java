package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.WhiteServiceImpl;
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
        WhiteServiceImpl whiteService = new WhiteServiceImpl();
        return id == BotServiceImpl.INSTANCE.getConfiguration(event.getBot().getId()).getMaster() || whiteService.exist(id);
    }

}
