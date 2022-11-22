package com.erzbir.mirai.numeron.sql;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/11/20 00:37
 * <p>
 * 数据库操作工具类, 目前的实现非常难以维护, 目标是通过获取注解的值来执行sql语句并注入进变量, 并且通过扫瞄实现了特定接口的类来为他们创建表
 * </p>
 */
public class SqlUtil {
    public static Map<String, Set<Object>> perms = new HashMap<>();
    public static Connection connection;
    public static Statement statement;

    static {
        perms.put("illegalList", new HashSet<>());
        perms.put("whiteList", new HashSet<>());
        perms.put("blackList", new HashSet<>());
        perms.put("groupList", new HashSet<>());
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
            statement = connection.createStatement();
            String sql = """
                    CREATE TABLE IF NOT EXISTS ACCESS(
                        USER_ID BIGINT PRIMARY KEY NOT NULL,
                        LIST_TYPE TEXT NOT NULL);
                                            
                    CREATE TABLE IF NOT EXISTS ILLEGAL(
                        KEY_WORD TEXT PRIMARY KEY NOT NULL);
                                            
                    CREATE TABLE IF NOT EXISTS GROUP_TABLE(
                        GROUP_ID BIGINT PRIMARY KEY NOT NULL)
                    """;
            statement.executeUpdate(sql);
            String findAll = "SELECT * from ACCESS";
            String findAll2 = "SELECT * from ILLEGAL";
            String findAll3 = "SELECT * from GROUP_TABLE";
            ResultSet resultSet = statement.executeQuery(findAll);
            while (resultSet.next()) {
                String type = resultSet.getString("LIST_TYPE");
                Long id = resultSet.getLong("USER_ID");
                if (!perms.containsKey(type)) {
                    perms.put(type, new HashSet<>());
                }
                perms.get(type).add(id);
            }
            resultSet = statement.executeQuery(findAll2);
            while (resultSet.next()) {
                String key = resultSet.getString("KEY_WORD");
                if (!perms.containsKey("illegalList")) {
                    perms.put("illegalList", new HashSet<>());
                }
                perms.get("illegalList").add(key);
            }
            resultSet = statement.executeQuery(findAll3);
            while (resultSet.next()) {
                Long id = resultSet.getLong("GROUP_ID");
                if (!perms.containsKey("groupList")) {
                    perms.put("groupList", new HashSet<>());
                }
                perms.get("groupList").add(id);
            }
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) throws SQLException {
        SqlUtil.add(375473609L);
        SqlUtil.add(780594692L);
        SqlUtil.add(329652880L);
    }

    public static boolean exist(String value) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM ILLEGAL WHERE KEY_WORD = '" + value + "'";
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
            resultSet.close();
            statement.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return false;
    }

    public static boolean exist(Long value, String type) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM ACCESS WHERE USER_ID = " + value;
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
            resultSet.close();
            statement.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return false;
    }

    public static boolean exist(Long value) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            String sql = "SELECT * FROM GROUP_TABLE WHERE GROUP_ID = " + value;
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
            resultSet.close();
            statement.close();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return false;
    }

    /**
     * 添加一个违禁词
     *
     * @param value 关键词
     */
    public static void add(String value) {
        if (exist(value)) {
            return;
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO ILLEGAL(KEY_WORD) " + "VALUES('" + value + "')";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 授权一个群
     *
     * @param value 群号
     */
    public static void add(Long value) {
        if (exist(value)) {
            return;
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO GROUP_TABLE(GROUP_ID) " + "VALUES(" + value + ")";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 添加一个qq号到一个组别
     *
     * @param value qq号
     * @param type  所属的组别, 目前只支持: blackList, whiteList
     */
    public static void add(Long value, String type) {
        if (exist(value, null)) {
            return;
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "INSERT INTO ACCESS(USER_ID, LIST_TYPE) VALUES(" + value + ", '" + type + "')";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 删除违禁词
     *
     * @param value 关键词
     */
    public static void remove(String value) {
        if (!exist(value)) {
            return;
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM ILLEGAL WHERE KEY_WORD = '" + value + "'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 将一个qq号从对应组移出
     *
     * @param value qq号
     * @param type  所属的组别, 目前只支持: blackList, whiteList
     */
    public static void remove(Long value, String type) {
        if (!exist(value, null)) {
            return;
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM ACCESS WHERE USER_ID = " + value + " and LIST_TYPE = '" + type + "'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * 移出groupList相当于取消该群授权
     *
     * @param value 群号
     */
    public static void remove(Long value) {
        if (!exist(value)) {
            return;
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM GROUP_TABLE WHERE GROUP_ID = " + value;
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
