package com.erzbir.numeron.api.filter.target;

import com.erzbir.numeron.api.filter.ChannelFilter;
import net.mamoe.mirai.event.events.GroupEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class GroupChannelFilter extends AbstractContactChannelFilter<GroupEvent> implements ChannelFilter<GroupEvent> {

    @Override
    public boolean filter(GroupEvent event) {
        return event.getGroup().getId() == id || id == 0;
    }
}
