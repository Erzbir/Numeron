package com.erzbir.mirai.numeron.entity;

import com.erzbir.mirai.numeron.sql.SqlConnection;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:11
 */

@Getter
public class BlackList {
    public static BlackList INSTANCE = new BlackList();

    static {
        String sql = """
                CREATE TABLE IF NOT EXISTS BLACKS(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * from BLACKS";
        ResultSet resultSet = null;
        try (Statement statement = SqlConnection.connection.createStatement()) {
            statement.executeUpdate(sql);
            resultSet = statement.executeQuery(findAll);
            while (resultSet.next()) {
                Long id = resultSet.getLong("ID");
                INSTANCE.black.add(id);
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

    private final HashSet<Long> black = new HashSet<>();

    private boolean exist(Long value) {
        ResultSet resultSet;
        try (Statement statement = SqlConnection.connection.createStatement()) {
            String sql = "SELECT * FROM BLACKS WHERE ID = " + value;
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

    private void addS(Long value, Long id) {
        if (exist(value)) {
            return;
        }
        try (Statement statement = SqlConnection.connection.createStatement()) {
            String sql = "INSERT INTO BLACKS(ID, OP_ID, OP_TIME) " +
                    "VALUES(" + value + ", " + id + ", '" + LocalTime.now() + "' " + ")";
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
        try (Statement statement = SqlConnection.connection.createStatement()) {
            String sql = "DELETE FROM BLACKS WHERE ID = " + value;
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    private void addD(Long value) {
        black.remove(value);
    }

    private void removeD(Long value) {
        black.remove(value);
    }

    public void add(Long value, Long id) {
        addD(value);
        new Thread(() -> addS(value, id)).start();
    }

    public void remove(Long value) {
        removeD(value);
        new Thread(() -> removeS(value));
    }
}
