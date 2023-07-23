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
public abstract class AbstractCacheFilterFactory implements FilterFactory, CacheFilterFactory {
    protected Map<Class<? extends Filter<?>>, Filter<? extends Event>> filterMap = new HashMap<>(8);

    public Filter<? extends Event> getFilter(Class<? extends Filter<? extends Event>> filerClass) {
        Filter<? extends Event> filter = filterMap.get(filerClass);
        if (filter == null) {
            try {
                filter = filerClass.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                NumeronLogUtil.logger.error(e);
            }
            filterMap.put(filerClass, filter);
        }
        return filter;
    }
}
