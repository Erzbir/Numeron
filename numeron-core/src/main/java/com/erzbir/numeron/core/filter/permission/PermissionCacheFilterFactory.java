package com.erzbir.numeron.core.filter.permission;

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
    public AbstractPermissionChannelFilter create(Enum<?> e) {
        if (e.equals(PermissionType.ALL)) {
            return (AbstractPermissionChannelFilter) getFilter(AllPermissionChannelFilter.class);
        } else if (e.equals(PermissionType.ADMIN)) {
            return (AbstractPermissionChannelFilter) getFilter(AdminPermissionChannelFilter.class);
        } else if (e.equals(PermissionType.FRIEND)) {
            return (AbstractPermissionChannelFilter) getFilter(FriendPermissionChannelFilter.class);
        } else if (e.equals(PermissionType.MASTER)) {
            return (AbstractPermissionChannelFilter) getFilter(MasterPermissionChannelFilter.class);
        } else if (e.equals(PermissionType.WHITE)) {
            return (AbstractPermissionChannelFilter) getFilter(WhitePermissionChannelFilter.class);
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
