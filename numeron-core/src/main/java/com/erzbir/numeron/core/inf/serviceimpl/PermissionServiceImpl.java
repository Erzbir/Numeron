package com.erzbir.numeron.core.inf.serviceimpl;

import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.Permission;
import com.erzbir.numeron.api.permission.PermissionService;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.core.inf.dao.PermissionDao;
import com.erzbir.numeron.core.inf.daoimpl.PermissionDaoImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class PermissionServiceImpl implements PermissionService {
    private final PermissionDao permissionDao = PermissionDaoImpl.INSTANCE;
    private Map<ContactType, Map<Long, Permission>> permissionMap;

    public PermissionServiceImpl() {
        try {
            permissionMap = permissionDao.getAllPermissions();
        } catch (SQLException e) {
            NumeronLogUtil.err(e.getMessage());
            permissionMap = new HashMap<>();
            permissionMap.put(ContactType.GROUP, new HashMap<>());
            permissionMap.put(ContactType.USER, new HashMap<>());
        }
    }


    @Override
    public void addPermission(ContactType contactType, Long contactId, Permission permission) {
        boolean flag = permissionMap.get(contactType).containsKey(contactId);
        permissionMap.get(contactType).put(contactId, permission);
        CompletableFuture.runAsync(() -> {
            try {
                if (flag) {
                    permissionDao.addPermission(contactType.getCode(), contactId, permission.getCode());
                } else {
                    permissionDao.updatePermission(contactType.getCode(), contactId, permission.getCode());
                }
            } catch (SQLException e) {
                NumeronLogUtil.err(e.getMessage());
            }
        });
    }

    @Override
    public void permit(ContactType contactType, Long contactId) {
        addPermission(contactType, contactId, PermissionType.NORMAL);
    }

    @Override
    public void ban(ContactType contactType, Long contactId) {
        addPermission(contactType, contactId, PermissionType.NONE);
    }

    @Override
    public void up(ContactType contactType, Long contactId) {
        Map<Long, Permission> contactPermissionMap = permissionMap.get(contactType);
        Permission permission = contactPermissionMap.get(contactId);
        PermissionType permissionType = PermissionType.valueOf(permission.getCode() - 1);
        contactPermissionMap.put(contactId, permissionType);

    }

    @Override
    public void down(ContactType contactType, Long contactId) {
        Map<Long, Permission> contactPermissionMap = permissionMap.get(contactType);
        Permission permission = contactPermissionMap.get(contactId);
        PermissionType permissionType = PermissionType.valueOf(permission.getCode() + 1);
        contactPermissionMap.put(contactId, permissionType);
    }


    @Override
    public void removePermission(ContactType contactType, Long contactId) {
        permissionMap.get(contactType).remove(contactId);
        CompletableFuture.runAsync(() -> {
            try {
                permissionDao.removePermission(contactType.getCode(), contactId);
            } catch (SQLException e) {
                NumeronLogUtil.err(e.getMessage());
            }
        });
    }

    @Override
    public Permission getContactPermission(ContactType contactType, Long contactId) {
        return permissionMap.get(contactType).get(contactId);
    }

    @Override
    public List<Long> getContactsOfPermission(ContactType contactType, Permission permission) {
        return permissionMap.values()
                .stream()
                .flatMap(map -> map.entrySet()
                        .stream()
                        .filter(p -> p.getValue()
                                .getCode() > permission.getCode() && !p.getValue().equals(PermissionType.NONE))
                        .map(Map.Entry::getKey))
                .toList();
    }

    @Override
    public Set<Long> getAllContact(ContactType contactType) {
        return permissionMap.get(contactType).keySet();
    }
}
