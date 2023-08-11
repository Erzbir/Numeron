package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.UserEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class UserChannelFilter extends AbstractContactChannelFilter<UserEvent> implements ChannelFilter<UserEvent> {
    @Override
    public boolean filter(UserEvent event) {
        return event.getUser().getId() == id || id == 0;
    }
}
