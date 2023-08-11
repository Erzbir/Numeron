package com.erzbir.numeron.api.filter.factory.target;

import com.erzbir.numeron.api.exception.FilterNotFoundException;
import com.erzbir.numeron.api.filter.TargetType;
import com.erzbir.numeron.api.filter.factory.EnumFilterFactory;
import com.erzbir.numeron.api.filter.target.*;
import net.mamoe.mirai.event.events.BotEvent;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public class TargetEnumFilterFactory implements EnumFilterFactory {
    public static final TargetEnumFilterFactory INSTANCE = new TargetEnumFilterFactory();

    private TargetEnumFilterFactory() {

    }

    @Override
    public AbstractContactChannelFilter<? extends BotEvent> create(Enum<?> e) {
        if (e.equals(TargetType.BOT)) {
            return new BotChannelFilter();
        } else if (e.equals(TargetType.FRIEND)) {
            return new FriendChannelFilter();
        } else if (e.equals(TargetType.USER)) {
            return new UserChannelFilter();
        } else if (e.equals(TargetType.STRANGER)) {
            return new StrangerChannelFilter();
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
