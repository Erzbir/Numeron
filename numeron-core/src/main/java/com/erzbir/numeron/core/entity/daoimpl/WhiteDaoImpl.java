package com.erzbir.numeron.core.entity.daoimpl;

import com.erzbir.numeron.core.entity.connection.SqlConnection;
import com.erzbir.numeron.core.entity.dao.WhiteDao;
import com.erzbir.numeron.utils.SqlUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2023/4/27 15:30
 */
public class WhiteDaoImpl implements WhiteDao {
    public static final WhiteDaoImpl INSTANCE = new WhiteDaoImpl();

    private WhiteDaoImpl() {
        String sql = """
                CREATE TABLE IF NOT EXISTS WHITES(
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
    public Set<Long> getWhites() throws SQLException {
        String sql = "SELECT * FROM WHITES";
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
    public boolean addWhite(long qq, long opId) throws SQLException {
        String sql = "INSERT INTO WHITES(ID,OP_ID,OP_TIME) VALUES(?,?,?)";
        Object[] params = {
                qq, opId, LocalDateTime.now()
        };
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        int flag = SqlUtil.executeUpdateSQL(prepared, params);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }

    @Override
    public boolean removeWhite(long qq) throws SQLException {
        String sql = "DELETE FROM GROUPS WHERE ID=?";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        prepared.setLong(1, qq);
        int flag = SqlUtil.executeUpdateSQL(prepared);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }
}
