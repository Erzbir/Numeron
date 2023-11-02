package com.erzbir.numeron.core.inf.daoimpl;

import com.erzbir.numeron.api.permission.ContactType;
import com.erzbir.numeron.api.permission.Permission;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.core.inf.connection.SqlConnection;
import com.erzbir.numeron.core.inf.dao.PermissionDao;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.erzbir.numeron.utils.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erzbir
 * @Date 2023/11/1
 */
public class PermissionDaoImpl implements PermissionDao {
    public final static PermissionDaoImpl INSTANCE = new PermissionDaoImpl();

    private PermissionDaoImpl() {
        String permission = """
                CREATE TABLE IF NOT EXISTS permissions(
                    id BIGINT PRIMARY KEY DEFAULT 0 NOT NULL,
                    contact_type_code INT DEFAULT 0 NOT NULL,
                    contact_id BIGINT NOT NULL,
                    permission_code INT NOT NULL
                    )
                """;
        try {
            PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(permission);
            SqlUtil.executeUpdateSQL(prepared);
            SqlUtil.closeResource(prepared);
        } catch (SQLException e) {
            NumeronLogUtil.err(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Permission> getAllPermissions(Integer contactTypeCode) throws SQLException {
        String sql = "SELECT * FROM permissions";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = SqlUtil.getResultSet(prepared);
        Map<Long, Permission> ret = new HashMap<>();
        while (resultSet.next()) {
            if (resultSet.getInt("contact_type_code") == contactTypeCode) {
                ret.put(resultSet.getLong("contact_id"), PermissionType.valueOf(resultSet.getInt("permission_code")));
            }
        }
        SqlUtil.closeResource(null, prepared, resultSet);
        return ret;
    }

    @Override
    public Map<ContactType, Map<Long, Permission>> getAllPermissions() throws SQLException {
        String sql = "SELECT * FROM permissions";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = SqlUtil.getResultSet(prepared);
        Map<ContactType, Map<Long, Permission>> ret = new HashMap<>();
        ret.putIfAbsent(ContactType.USER, new HashMap<>());
        ret.putIfAbsent(ContactType.GROUP, new HashMap<>());
        while (resultSet.next()) {
            int contactType = resultSet.getInt("contact_type_code");
            ContactType contactType1 = ContactType.valueOf(contactType);
            long contactId = resultSet.getLong("contact_id");
            PermissionType permissionCode = PermissionType.valueOf(resultSet.getInt("permission_code"));
            ret.get(contactType1).put(contactId, permissionCode);
        }
        SqlUtil.closeResource(null, prepared, resultSet);
        return ret;
    }

    @Override
    public void addPermission(Integer contactTypeCode, Long contactId, Integer permissionCode) throws SQLException {
        String sql = "INSERT INTO permissions(contact_type_code,contact_id,permission_code) VALUES(?,?,?)";
        Object[] params = {
                contactTypeCode, contactId, permissionCode
        };
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        SqlUtil.executeUpdateSQL(prepared, params);
        SqlUtil.closeResource(prepared);
    }

    @Override
    public void removePermission(Integer contactType, Long contactId) throws SQLException {
        String sql = "DELETE FROM permissions WHERE contact_type_code=? and contact_id=?";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        prepared.setInt(1, contactType);
        prepared.setLong(2, contactId);
        SqlUtil.executeUpdateSQL(prepared);
        SqlUtil.closeResource(prepared);
    }

    @Override
    public void updatePermission(Integer contactTypeCode, Long contactId, Integer permissionCode) throws SQLException {
        String sql = "UPDATE permissions SET permission_code=? WHERE contact_type_code=? and contact_id=?";
        Object[] params = {
                contactTypeCode, contactId, permissionCode
        };
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        SqlUtil.executeUpdateSQL(prepared, params);
        SqlUtil.closeResource(prepared);
    }
}
