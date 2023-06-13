package com.erzbir.numeron.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/9 18:11
 * <p>数据库工具类</p>
 */
public class SqlUtil {
    private SqlUtil() {

    }

    public static ResultSet getResultSet(PreparedStatement prepared, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            prepared.setObject(i + 1, params[i]);
        }
        return prepared.executeQuery();
    }

    public static int executeUpdateSQL(PreparedStatement prepared, Object... params) throws SQLException {
        int updateRows;
        for (int i = 0; i < params.length; i++) {
            prepared.setObject(i + 1, params[i]);
        }
        updateRows = prepared.executeUpdate();
        prepared.close();
        return updateRows;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void listInit(Connection connection, String sql, String find, Set set, String columnLabel, Class<?> type) throws SQLException {
        PreparedStatement prepared = connection.prepareStatement(sql);
        executeUpdateSQL(prepared);
        ResultSet resultSet = SqlUtil.getResultSet(prepared);
        while (resultSet.next()) {
            Object o = resultSet.getObject(columnLabel, type);
            set.add(o);
        }
        closeResource(null, prepared, resultSet);
    }

    public static boolean closeResource(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                NumeronLogUtil.logger.error(e);
                return false;
            }
        }
        return true;
    }

    public static boolean closeResource(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException e) {
                NumeronLogUtil.logger.error(e);
                return false;
            }
        }
        return true;
    }

    public static boolean closeResource(PreparedStatement prepared) {
        if (prepared != null) {
            try {
                prepared.close();
                prepared = null;
            } catch (SQLException e) {
                NumeronLogUtil.logger.error(e);
                return false;
            }
        }
        return true;
    }

    public static boolean closeResource(Connection connection, PreparedStatement prepared, ResultSet rs) {
        return closeResource(connection) && closeResource(prepared) && closeResource(rs);
    }
}
