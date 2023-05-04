package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.api.entity.AdminService;
import com.erzbir.numeron.api.entity.BlackService;
import com.erzbir.numeron.api.entity.WhiteService;
import com.erzbir.numeron.core.bot.NumeronBot;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 14:54
 * <p>群管理员权限过滤类, 过滤掉(舍弃)不是群管理员的event</p>
 */
public class AdminPermissionFilter extends AbstractPermissionFilter {
    public static final AdminPermissionFilter INSTANCE = new AdminPermissionFilter();

    private AdminPermissionFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event) {
        long id = event.getSender().getId();
        if (event instanceof GroupMessageEvent event1) {
            return NumeronBot.INSTANCE.getMaster() == id ||
                    (AdminService.INSTANCE.getAdmins(event1.getGroup().getId()).contains(id) && !BlackService.INSTANCE.exist(id))
                    || WhiteService.INSTANCE.exist(id);
        }
        return NumeronBot.INSTANCE.getMaster() == id;
    }
}
