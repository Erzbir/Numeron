package com.erzbir.numeron.plugin.qqmanage.action;


import com.erzbir.numeron.core.entity.connection.SqlConnection;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.erzbir.numeron.utils.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:32
 * <p>
 * 违禁词
 * </p>
 */

public final class IllegalService {
    public final static IllegalService INSTANCE = new IllegalService();
    private final Set<String> illegal = new HashSet<>();

    private IllegalService() {
        String sql = """
                CREATE TABLE IF NOT EXISTS ILLEGALS(
                    KEY BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        try {
            PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
            SqlUtil.executeUpdateSQL(prepared);
            SqlUtil.closeResource(prepared);
            illegal.addAll(init());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getIllegals() {
        return illegal;
    }

    public boolean add(String key, long opId) {
        illegal.add(key);
        try {
            return addIllegal(key, opId);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }

    public boolean remove(String key) {
        illegal.remove(key);
        try {
            return removeIllegal(key);
        } catch (SQLException e) {
            NumeronLogUtil.logger.error(e);
            e.printStackTrace();
        }
        return false;
    }

    public boolean exist(String key) {
        return illegal.contains(key);
    }


    private Set<String> init() throws SQLException {
        String sql = "SELECT * FROM ILLEGALS";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = SqlUtil.getResultSet(prepared);
        Set<String> ret = new HashSet<>();
        while (resultSet.next()) {
            ret.add(resultSet.getString("KEY"));
        }
        SqlUtil.closeResource(null, prepared, resultSet);
        return ret;
    }

    private boolean addIllegal(String key, long opId) throws SQLException {
        String sql = "INSERT INTO ILLEGALS(KEY,OP_ID,OP_TIME) VALUES(?,?,?)";
        Object[] params = {
                key, opId, LocalDateTime.now()
        };
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        int flag = SqlUtil.executeUpdateSQL(prepared, params);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }

    private boolean removeIllegal(String key) throws SQLException {
        String sql = "DELETE FROM ILLEGALS WHERE KEY=?";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        prepared.setString(1, key);
        int flag = SqlUtil.executeUpdateSQL(prepared);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }
}
