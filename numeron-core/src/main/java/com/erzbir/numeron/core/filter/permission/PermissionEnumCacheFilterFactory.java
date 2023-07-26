package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.AbstractEnumCacheFilterFactory;
import com.erzbir.numeron.core.filter.CacheFilterFactory;
import com.erzbir.numeron.core.filter.EnumFilterFactory;
import com.erzbir.numeron.filter.PermissionType;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class PermissionEnumCacheFilterFactory extends AbstractEnumCacheFilterFactory implements EnumFilterFactory, CacheFilterFactory {
    public final static PermissionEnumCacheFilterFactory INSTANCE = new PermissionEnumCacheFilterFactory();

    private PermissionEnumCacheFilterFactory() {

    }

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
