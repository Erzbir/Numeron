package com.erzbir.numeron.core.filter.permission;

import com.erzbir.numeron.core.filter.ChannelFilterInter;
import com.erzbir.numeron.core.filter.FilterFactory;

/**
 * @author Erzbir
 * @Date: 2022/11/26 17:00
 * <p>工厂</P>
 */
public class PermissionFilterFactory implements FilterFactory {
    public final static PermissionFilterFactory INSTANCE = new PermissionFilterFactory();

    private PermissionFilterFactory() {

    }

    /**
     * @param e 枚举子类
     * @return ChannelFilterInter
     */
    @Override
    public ChannelFilterInter create(Enum<?> e, String text) {
        if (e.equals(PermissionType.ALL)) {
            return AllPermissionFilter.INSTANCE;
        } else if (e.equals(PermissionType.MASTER)) {
            return MasterPermissionFilter.INSTANCE;
        } else if (e.equals(PermissionType.WHITE)) {
            return WhitePermissionFilter.INSTANCE;
        } else if (e.equals(PermissionType.ADMIN)) {
            return AdminPermissionFilter.INSTANCE;
        } else if (e.equals(PermissionType.FRIEND)) {
            return FriendPermissionFilter.INSTANCE;
        }
        return null;
    }
}
