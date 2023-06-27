package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.AdminServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.BlackServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.WhiteServiceImpl;
import com.erzbir.numeron.core.filter.EventFilter;
import com.erzbir.numeron.core.filter.Filter;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/3/8 14:54
 * <p>群管理员权限过滤类, 过滤掉(舍弃)不是群管理员的 event</p>
 */
public class AdminPermissionFilter extends AbstractPermissionFilter implements EventFilter<MessageEvent> {
    public AdminPermissionFilter(Filter filter) {
        super(filter);
    }

    @Override
    public void filter(MessageEvent event) {
        setFilterRule(t -> filter0(event), event);
    }

    private Boolean filter0(MessageEvent event) {
        long id = event.getSender().getId();
        long botId = event.getBot().getId();
        long master = BotServiceImpl.INSTANCE.getMaster(event.getBot());
        AdminServiceImpl adminService = new AdminServiceImpl();
        WhiteServiceImpl whiteService = new WhiteServiceImpl();
        BlackServiceImpl blackService = new BlackServiceImpl();
        if (event instanceof GroupMessageEvent event1) {
            return master == id ||
                    (adminService.exist(botId, event1.getGroup().getId(), id) && !blackService.exist(id))
                    || whiteService.exist(id);
        }
        return master == id;
    }

    @Override
    public boolean filter() {
        return super.filter() && filterRule.test(arg);
    }
}