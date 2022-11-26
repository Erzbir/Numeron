package com.erzbir.mirai.numeron.entity;

import com.erzbir.mirai.numeron.sql.SqlUtil;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:29
 */
@Getter
public class GroupList {
    public static GroupList INSTANCE = new GroupList();

    static {
        String sql = """
                CREATE TABLE IF NOT EXISTS GROUPS(
                    ID BIGINT PRIMARY KEY NOT NULL
                    )
                """;
        String findAll = "SELECT * FROM GROUPS";
        ResultSet resultSet = null;
        try (Statement statement = SqlUtil.connection.createStatement()) {
            statement.executeUpdate(sql);
            resultSet = statement.executeQuery(findAll);
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                INSTANCE.group.add(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private final HashSet<Long> group = new HashSet<>();

    private boolean exist(Long value) {
        ResultSet resultSet;
        try (Statement statement = SqlUtil.connection.createStatement()) {
            String sql = "SELECT * FROM GROUPS WHERE ID = " + value;
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return true;
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return false;
    }

    private void addS(Long value) {
        if (exist(value)) {
            return;
        }
        try (Statement statement = SqlUtil.connection.createStatement()) {
            String sql = "INSERT INTO GROUPS(ID) VALUES(" + value + ")";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void removeS(Long value) {
        if (!exist(value)) {
            return;
        }
        try (Statement statement = SqlUtil.connection.createStatement()) {
            String sql = "DELETE FROM GROUPS WHERE ID = " + value;
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    private void addD(Long value) {
        group.add(value);
    }

    private void removeD(Long value) {
        group.remove(value);
    }

    public void add(Long value) {
        addD(value);
        new Thread(() -> addS(value)).start();
    }

    public void remove(Long value) {
        removeD(value);
        new Thread(() -> removeS(value)).start();
    }
}
