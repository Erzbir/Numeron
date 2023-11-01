package com.erzbir.numeron.api.filter;

import com.erzbir.numeron.api.filter.factory.CacheFilterFactory;
import com.erzbir.numeron.utils.NumeronLogUtil;
import net.mamoe.mirai.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date 2023/7/27
 */
public abstract class AbstractCacheFilter implements CacheFilterFactory {
    protected Map<Class<? extends Filter<?>>, Filter<? extends Event>> filterMap = new HashMap<>(8);

    public Filter<? extends Event> getFilter(Class<? extends Filter<? extends Event>> filerClass) {
        Filter<? extends Event> channelFilter = filterMap.get(filerClass);
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
