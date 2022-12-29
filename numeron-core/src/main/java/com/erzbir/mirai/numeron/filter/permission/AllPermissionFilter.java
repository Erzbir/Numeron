package com.erzbir.mirai.numeron.filter.permission;

import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * 实体权限过滤类
 */
public class AllPermissionFilter extends AbstractPermissionFilter {
    public static final AllPermissionFilter INSTANCE = new AllPermissionFilter();

    private AllPermissionFilter() {
    }

    @Override
    public Boolean filter(MessageEvent event, String text) {
        return true;
    }
}
