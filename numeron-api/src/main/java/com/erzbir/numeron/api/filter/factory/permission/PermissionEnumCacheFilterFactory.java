package com.erzbir.numeron.api.filter.factory.permission;

import com.erzbir.numeron.api.exception.FilterNotFoundException;
import com.erzbir.numeron.api.filter.AbstractCacheFilter;
import com.erzbir.numeron.api.filter.PermissionType;
import com.erzbir.numeron.api.filter.factory.CacheFilterFactory;
import com.erzbir.numeron.api.filter.factory.EnumFilterFactory;
import com.erzbir.numeron.api.filter.permission.*;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class PermissionEnumCacheFilterFactory extends AbstractCacheFilter implements EnumFilterFactory, CacheFilterFactory {
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
