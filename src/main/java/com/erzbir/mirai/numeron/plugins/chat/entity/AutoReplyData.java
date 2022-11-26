package com.erzbir.mirai.numeron.plugins.chat.entity;

import com.erzbir.mirai.numeron.sql.SqlUtil;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2022/11/25 17:21
 */
@Getter
public class AutoReplyData {
    public static AutoReplyData INSTANCE = new AutoReplyData();

    static {
        String sql = """
                CREATE TABLE IF NOT EXISTS CHAT(
                KEY TEXT PRIMARY KEY NOT NULL,
                ANSWER TEXT)
                """;
        String findAll = "SELECT * from CHAT";
        ResultSet resultSet = null;
        try (Statement statement = SqlUtil.connection.createStatement()) {
            statement.executeUpdate(sql);
            resultSet = statement.executeQuery(findAll);
            while (resultSet.next()) {
                String key = resultSet.getString("KEY");
                String answer = resultSet.getString("ANSWER");
                INSTANCE.getData().put(key, answer);
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

    private final HashMap<String, String> data = new HashMap<>();

    private AutoReplyData() {
    }

    public void add(String key, String answer) {
        addD(key, answer);
        addS(key, answer);
    }

    public void remove(String key, String answer) {
        removeD(key);
        removeS(key, answer);
    }

    private void addD(String key, String answer) {
        data.put(key, answer);
    }

    private void removeD(String key) {
        data.remove(key);
    }

    private void addS(String key, String answer) {
        String sql = "INSERT INTO CHAT(KEY, ANSWER) VALUES('" + key + "', '" + answer + "')";
        if (exist(key)) {
            sql = "UPDATE CHAT SET ANSWER = '" + answer + "' WHERE KEY = '" + answer + "'";
        }
        try (Statement statement = SqlUtil.connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void removeS(String key, String answer) {
        if (!exist(key)) {
            return;
        }
        try (Statement statement = SqlUtil.connection.createStatement()) {
            String sql = "DELETE FROM CHAT WHERE KEY = '" + key + "' and ANSWER = '" + answer + "'";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public String getAnswer(String key) {
        return data.get(key);
    }

    public boolean exist(String key) {
        ResultSet resultSet;
        try (Statement statement = SqlUtil.connection.createStatement()) {
            String sql = "SELECT * FROM CHAT WHERE KEY = '" + key + "'";
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
}
