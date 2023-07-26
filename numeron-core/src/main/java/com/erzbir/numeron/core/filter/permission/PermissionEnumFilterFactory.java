package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.exception.FilterNotFoundException;
import com.erzbir.numeron.core.filter.EnumFilterFactory;
import com.erzbir.numeron.filter.PermissionType;

/**
 * @author Erzbir
 * @Date: 2022/11/26 16:30
 * <p>规则过滤器工厂</p>
 */
public class PermissionEnumFilterFactory implements EnumFilterFactory {
    public final static PermissionEnumFilterFactory INSTANCE = new PermissionEnumFilterFactory();

    private PermissionEnumFilterFactory() {

    }


    @Override
    public AbstractPermissionChannelFilter create(Enum<?> e) {
        if (e.equals(PermissionType.ALL)) {
            return new AllPermissionChannelFilter();
        } else if (e.equals(PermissionType.ADMIN)) {
            return new AdminPermissionChannelFilter();
        } else if (e.equals(PermissionType.FRIEND)) {
            return new FriendPermissionChannelFilter();
        } else if (e.equals(PermissionType.MASTER)) {
            return new MasterPermissionChannelFilter();
        } else if (e.equals(PermissionType.WHITE)) {
            return new WhitePermissionChannelFilter();
        }
        throw new FilterNotFoundException("no filter of this type");
    }
}
