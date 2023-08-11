package com.erzbir.numeron.api.filter.permission;

import com.erzbir.numeron.api.bot.BotService;
import com.erzbir.numeron.api.bot.BotServiceImpl;
import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:03
 * <p>主人权限过滤类, 过滤掉(舍弃)不是主人的event</p>
 */
public class MasterPermissionChannelFilter extends AbstractPermissionChannelFilter implements ChannelFilter<MessageEvent> {
    private final BotService botService = BotServiceImpl.INSTANCE;

    @Override
    public boolean filter(MessageEvent event) {
        return botService.getMaster(event.getBot()) == event.getSender().getId();
    }
}
