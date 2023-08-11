package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.FriendEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class FriendChannelFilter extends AbstractContactChannelFilter<FriendEvent> implements ChannelFilter<FriendEvent> {
    @Override
    public boolean filter(FriendEvent event) {
        return id == event.getFriend().getId() || id == 0;
    }
}
