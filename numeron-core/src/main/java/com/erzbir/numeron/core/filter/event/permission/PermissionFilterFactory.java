package com.erzbir.numeron.core.filter.event.permission;

import com.erzbir.numeron.core.filter.Filter;
import com.erzbir.numeron.core.filter.FilterFactory;
import com.erzbir.numeron.filter.PermissionType;

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
    public AbstractPermissionFilter create(Enum<?> e, Filter filter) {
        if (e.equals(PermissionType.ALL)) {
            return new AllPermissionFilter(filter);
        } else if (e.equals(PermissionType.MASTER)) {
            return new MasterPermissionFilter(filter);
        } else if (e.equals(PermissionType.WHITE)) {
            return new WhitePermissionFilter(filter);
        } else if (e.equals(PermissionType.ADMIN)) {
            return new AdminPermissionFilter(filter);
        } else if (e.equals(PermissionType.FRIEND)) {
            return new FriendPermissionFilter(filter);
        }
        return null;
    }
}
