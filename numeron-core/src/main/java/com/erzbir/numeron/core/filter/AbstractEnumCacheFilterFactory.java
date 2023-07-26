package com.erzbir.numeron.core.filter;


import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Data 2023/7/23
 */
public abstract class AbstractEnumCacheFilterFactory implements EnumFilterFactory, CacheFilterFactory {
    protected Map<Class<? extends ChannelFilter<?>>, ChannelFilter<? extends Event>> filterMap = new HashMap<>(8);

    public ChannelFilter<? extends Event> getFilter(Class<? extends ChannelFilter<? extends Event>> filerClass) {
        ChannelFilter<? extends Event> channelFilter = filterMap.get(filerClass);
        if (channelFilter == null) {
            try {
                channelFilter = filerClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                NumeronLogUtil.logger.error(e);
            }
            filterMap.put(filerClass, channelFilter);
        }
        return channelFilter;
    }
}
