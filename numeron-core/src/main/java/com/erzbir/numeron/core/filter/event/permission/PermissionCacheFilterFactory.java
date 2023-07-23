package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.AbstractCacheFilterFactory;
import com.erzbir.numeron.core.filter.CacheFilterFactory;
import com.erzbir.numeron.core.filter.FilterFactory;
import com.erzbir.numeron.filter.PermissionType;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class PermissionCacheFilterFactory extends AbstractCacheFilterFactory implements FilterFactory, CacheFilterFactory {
    public final static PermissionCacheFilterFactory INSTANCE = new PermissionCacheFilterFactory();

    private PermissionCacheFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public AbstractPermissionFilter create(Enum<?> e) {
        if (e.equals(PermissionType.ALL)) {
            return (AbstractPermissionFilter) getFilter(AllPermissionFilter.class);
        } else if (e.equals(PermissionType.ADMIN)) {
            return (AbstractPermissionFilter) getFilter(AdminPermissionFilter.class);
        } else if (e.equals(PermissionType.FRIEND)) {
            return (AbstractPermissionFilter) getFilter(FriendPermissionFilter.class);
        } else if (e.equals(PermissionType.MASTER)) {
            return (AbstractPermissionFilter) getFilter(MasterPermissionFilter.class);
        } else if (e.equals(PermissionType.WHITE)) {
            return (AbstractPermissionFilter) getFilter(WhitePermissionFilter.class);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
