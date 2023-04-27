package com.erzbir.numeron.core.entity.daoimpl;

import com.erzbir.numeron.core.entity.connection.SqlConnection;
import com.erzbir.numeron.core.entity.dao.BlackDao;
import com.erzbir.numeron.utils.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 15:29
 */
public class BlackDaoImpl implements BlackDao {
    public static final BlackDaoImpl INSTANCE = new BlackDaoImpl();

    private BlackDaoImpl() {
        String sql = """
                CREATE TABLE IF NOT EXISTS BLACKS(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        try {
            PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
            SqlUtil.executeUpdateSQL(prepared);
            SqlUtil.closeResource(prepared);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Set<Long> getBlacks() throws SQLException {
        String sql = "SELECT * FROM BLACKS";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        ResultSet resultSet = SqlUtil.getResultSet(prepared);
        Set<Long> ret = new HashSet<>();
        while (resultSet.next()) {
            ret.add(resultSet.getLong("ID"));
        }
        SqlUtil.closeResource(null, prepared, resultSet);
        return ret;
    }

    @Override
    public boolean addBlack(long qq, long opId) throws SQLException {
        String sql = "INSERT INTO BLACKS(ID,OP_ID,OP_TIME) VALUES(?,?,?)";
        Object[] params = {
                qq, opId, LocalDateTime.now()
        };
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        int flag = SqlUtil.executeUpdateSQL(prepared, params);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }

    @Override
    public boolean removeBlack(long qq) throws SQLException {
        String sql = "DELETE FROM BLACKS WHERE ID=?";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        prepared.setLong(1, qq);
        int flag = SqlUtil.executeUpdateSQL(prepared);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }
}
