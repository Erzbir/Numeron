package com.erzbir.numeron.api.filter.permission;

import com.erzbir.numeron.api.bot.BotService;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.entity.WhiteService;
import com.erzbir.numeron.api.entity.WhiteServiceImpl;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>白名单权限过滤类, 过滤掉(舍弃)不是白名单的event</p>
 */
public class WhitePermissionChannelFilter extends AbstractPermissionChannelFilter implements ChannelFilter<MessageEvent> {
    private final WhiteService whiteService = WhiteServiceImpl.INSTANCE;
    private final BotService botService = BotServiceImpl.INSTANCE;

    @Override
    public boolean filter(MessageEvent event) {
        long id = event.getSender().getId();
        return id == botService.getMaster(event.getBot()) || whiteService.exist(id);
    }
}
