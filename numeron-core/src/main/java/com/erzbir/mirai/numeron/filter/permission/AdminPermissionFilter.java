package com.erzbir.mirai.numeron.filter.permission;

import com.erzbir.mirai.numeron.entity.AdminList;
import com.erzbir.mirai.numeron.entity.BlackList;
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
    public Boolean filter(MessageEvent event, String text) {
        if (event instanceof GroupMessageEvent event1) {
            long id = event1.getSender().getId();
            return AdminList.INSTANCE.getAdmins(event1.getGroup().getId()).contains(id) && !BlackList.INSTANCE.contains(id);
        }
        return false;
    }
}
