package com.erzbir.mirai.numeron.sql;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Erzbir
 * @Date: 2022/11/20 00:37
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
        SqlUtil.add("sadasdad");
        SqlUtil.add("sadashiopjposadadsdad");
        SqlUtil.add("sadasasdasddad");
        SqlUtil.add(1221L, "blackList");
        SqlUtil.add(12312L, "whiteList");
        SqlUtil.add(4311221L, "blackList");
        SqlUtil.add(314112312L, "whiteList");
        SqlUtil.add(43141221L, "blackList");
        SqlUtil.add(311231221L, "whiteList");
        SqlUtil.add(375473609L);
        SqlUtil.add(780594692L);
        SqlUtil.add(1235242L);
        SqlUtil.add(12421431313L);
        SqlUtil.remove(1221L);
        SqlUtil.remove(12312L, "whiteList");
        SqlUtil.remove("sadasdad");
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
