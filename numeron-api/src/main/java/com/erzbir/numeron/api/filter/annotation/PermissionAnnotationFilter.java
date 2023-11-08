package com.erzbir.numeron.api.filter.annotation;

import com.erzbir.numeron.annotation.Permission;
import com.erzbir.numeron.api.filter.AnnotationFilter;
import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.PermissionManager;
import com.erzbir.numeron.api.permission.PermissionType;
import net.mamoe.mirai.event.events.GroupEvent;
import net.mamoe.mirai.event.events.UserEvent;

/**
 * 针对 {@link Permission } 注解的内容过滤
 *
 * @author Erzbir
 * @Date 2023/11/1
 * @see Permission
 */
public class PermissionAnnotationFilter extends AbstractAnnotationFilter<Permission> implements AnnotationFilter {
    @Override
    public <E> boolean filter(E event) {
        if (annotation == null) {
            return true;
        }
        PermissionType permissionType = annotation.permission();
        if (permissionType.equals(PermissionType.EVERYONE)) {
            return true;
        }
        if (permissionType.equals(PermissionType.NONE)) {
            return false;
        }
        if (event instanceof UserEvent userEvent) {
            com.erzbir.numeron.api.permission.Permission userPermission = PermissionManager.INSTANCE.getContactPermission(ContactType.USER, userEvent.getUser().getId());
            if (userPermission == null || userPermission.getCode() == PermissionType.NONE.getCode()) {
                return false;
            }
            return userPermission.getCode() <= permissionType.getCode();
        } else if (event instanceof GroupEvent groupEvent) {
            com.erzbir.numeron.api.permission.Permission groupPermission = PermissionManager.INSTANCE.getContactPermission(ContactType.GROUP, groupEvent.getGroup().getId());
            if (groupPermission == null || groupPermission.getCode() == PermissionType.NONE.getCode()) {
                return false;
            }
            return groupPermission.getCode() <= permissionType.getCode();
        }
        return true;
    }
}
