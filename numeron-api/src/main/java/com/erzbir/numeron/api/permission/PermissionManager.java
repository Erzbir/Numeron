package com.erzbir.numeron.api.permission;

import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class PermissionManager implements PermissionService {

    public static final PermissionManager INSTANCE = new PermissionManager();
    private final PermissionService permissionService = ServiceLoader.load(PermissionService.class).stream().findFirst().orElseThrow().get();

    private PermissionManager() {
    }

    @Override
    public void addPermission(ContactType contactType, Long contactId, Permission permission) {
        permissionService.addPermission(contactType, contactId, permission);
    }

    @Override
    public void permit(ContactType contactType, Long contactId) {
        permissionService.permit(contactType, contactId);
    }

    @Override
    public void ban(ContactType contactType, Long contactId) {
        permissionService.ban(contactType, contactId);
    }

    @Override
    public void up(ContactType contactType, Long contactId) {
        permissionService.up(contactType, contactId);

    }

    @Override
    public void down(ContactType contactType, Long contactId) {
        permissionService.down(contactType, contactId);
    }


    @Override
    public void removePermission(ContactType contactType, Long contactId) {
        permissionService.removePermission(contactType, contactId);
    }

    @Override
    public Permission getContactPermission(ContactType contactType, Long contactId) {
        return permissionService.getContactPermission(contactType, contactId);
    }

    @Override
    public List<Long> getContactsOfPermission(ContactType contactType, Permission permission) {
        return permissionService.getContactsOfPermission(contactType, permission);
    }

    @Override
    public Set<Long> getAllContact(ContactType contactType) {
        return permissionService.getAllContact(contactType);
    }
}
