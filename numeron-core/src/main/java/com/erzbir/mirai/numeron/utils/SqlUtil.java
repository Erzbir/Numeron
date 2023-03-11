package com.erzbir.mirai.numeron.utils;


import com.erzbir.mirai.numeron.entity.SqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/12/9 18:11
 * <p>数据库工具类</p>
 */
public class SqlUtil {
    private final static Connection connection = SqlConnection.getConnection();
    private static final Statement statement;

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SqlUtil() {

    }

    public static ResultSet getResultSet(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public static void executeUpdateSQL(String sql) throws SQLException {
        statement.executeUpdate(sql);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void listInit(String sql, String find, Set set, String columnLabel, Class<?> type) throws SQLException {
        executeUpdateSQL(sql);
        ResultSet resultSet = SqlUtil.getResultSet(find);
        while (resultSet.next()) {
            Object o = resultSet.getObject(columnLabel, type);
            set.add(o);
        }
    }
}
