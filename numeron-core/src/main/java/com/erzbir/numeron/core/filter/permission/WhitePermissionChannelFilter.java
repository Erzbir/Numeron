package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.bot.BotServiceImpl;
import com.erzbir.numeron.core.entity.serviceimpl.WhiteServiceImpl;
import com.erzbir.numeron.core.filter.ChannelFilter;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/26 15:54
 * <p>白名单权限过滤类, 过滤掉(舍弃)不是白名单的event</p>
 * TODO 可以根据处理的注解创建对应的注解处理器类, 组装出对应的过滤器做成静态变量放入其中, 这样可以在以后通过反射加入自定义扩展过滤
 */
public class WhitePermissionChannelFilter extends AbstractPermissionChannelFilter implements ChannelFilter<MessageEvent> {
    @Override
    public boolean filter(MessageEvent event) {
        long id = event.getSender().getId();
        WhiteServiceImpl whiteService = new WhiteServiceImpl();
        BotServiceImpl botService = new BotServiceImpl();
        return id == botService.getMaster(event.getBot()) || whiteService.exist(id);
    }
}
