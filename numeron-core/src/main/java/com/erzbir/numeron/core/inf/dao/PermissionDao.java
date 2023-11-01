package com.erzbir.numeron.core.inf.dao;

import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.Permission;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public interface PermissionDao {
    Map<Long, Permission> getAllPermissions(Integer contactTypeCode) throws SQLException;

    Map<ContactType, Map<Long, Permission>> getAllPermissions() throws SQLException;

    void addPermission(Integer contactTypeCode, Long contactId, Integer permission_code) throws SQLException;

    void removePermission(Integer contactTypeCode, Long contactId) throws SQLException;

    void updatePermission(Integer contactTypeCode, Long contactId, Integer permissionCode) throws SQLException;
}
