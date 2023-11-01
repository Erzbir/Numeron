package com.erzbir.numeron.api.permission;

import java.util.List;
import java.util.Set;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public interface PermissionService {
    void addPermission(ContactType contactType, Long contactId, Permission permission);

    void permit(ContactType contactType, Long contactId);

    void ban(ContactType contactType, Long contactId);

    void up(ContactType contactType, Long contactId);

    void down(ContactType contactType, Long contactId);

    void removePermission(ContactType contactType, Long contactId);

    Permission getContactPermission(ContactType contactType, Long contactId);

    List<Long> getContactsOfPermission(ContactType contactType, Permission permission);

    Set<Long> getAllContact(ContactType contactType);
}
