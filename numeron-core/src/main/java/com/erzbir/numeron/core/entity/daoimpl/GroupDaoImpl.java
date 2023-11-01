package com.erzbir.numeron.core.entity.daoimpl;

import com.erzbir.numeron.core.entity.connection.SqlConnection;
import com.erzbir.numeron.core.entity.dao.GroupDao;
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
public class GroupDaoImpl implements GroupDao {
    public static final GroupDaoImpl INSTANCE = new GroupDaoImpl();

    private GroupDaoImpl() {
        String sql = """
                CREATE TABLE IF NOT EXISTS GROUPS(
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
    public Set<Long> getEnableGroups() throws SQLException {
        String sql = "SELECT * FROM GROUPS";
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
    public boolean enableGroup(long groupId, long opId) throws SQLException {
        String sql = "INSERT INTO GROUPS(ID,OP_ID,OP_TIME) VALUES(?,?,?)";
        Object[] params = {
                groupId, opId, LocalDateTime.now()
        };
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        int flag = SqlUtil.executeUpdateSQL(prepared, params);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }

    @Override
    public boolean disableGroup(long groupId) throws SQLException {
        String sql = "DELETE FROM GROUPS WHERE ID=?";
        PreparedStatement prepared = SqlConnection.getConnection().prepareStatement(sql);
        prepared.setLong(1, groupId);
        int flag = SqlUtil.executeUpdateSQL(prepared);
        SqlUtil.closeResource(prepared);
        return flag >= 1;
    }
}
