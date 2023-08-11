package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.ListenTarget;
import com.erzbir.numeron.api.filter.ChannelFilter;
import com.erzbir.numeron.api.filter.TargetType;
import com.erzbir.numeron.api.filter.factory.target.TargetEnumFilterFactory;
import com.erzbir.numeron.api.filter.target.AbstractContactChannelFilter;
import net.mamoe.mirai.event.events.BotEvent;


/**
 * @author Erzbir
 * @Date: 2023/7/5 12:03
 */
public class TargetAnnotationChannelFilter extends AbstractAnnotationChannelFilter<ListenTarget, BotEvent> implements ChannelFilter<BotEvent> {
    @Override
    public boolean filter(BotEvent event) {
        long id = annotation.id();
        TargetType targetType = annotation.value();
        if (id == 0) {
            return true;
        }
        @SuppressWarnings("unchecked")
        AbstractContactChannelFilter<BotEvent> filter = (AbstractContactChannelFilter<BotEvent>) TargetEnumFilterFactory.INSTANCE.create(targetType);
        filter.setId(id);
        return filter.filter(event);
    }

}
