package com.erzbir.numeron.api.filter.permission;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.entity.*;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 14:54
 * <p>群管理员权限过滤类, 过滤掉(舍弃)不是群管理员的 event</p>
 */
public class AdminPermissionChannelFilter extends AbstractPermissionChannelFilter implements ChannelFilter<MessageEvent> {
    private final AdminService adminService = AdminServiceImpl.INSTANCE;
    private final WhiteService whiteService = WhiteServiceImpl.INSTANCE;
    private final BlackService blackService = BlackServiceImpl.INSTANCE;

    @Override
    public boolean filter(MessageEvent event) {
        long id = event.getSender().getId();
        long botId = event.getBot().getId();
        long master = BotServiceImpl.INSTANCE.getMaster(event.getBot());
        if (event instanceof GroupMessageEvent event1) {
            return master == id ||
                    (adminService.exist(botId, event1.getGroup().getId(), id) && !blackService.exist(id))
                    || whiteService.exist(id);
        }
        return master == id;
    }
}
