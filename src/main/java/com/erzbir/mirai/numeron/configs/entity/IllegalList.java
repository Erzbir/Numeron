package com.erzbir.mirai.numeron.configs.entity;

import com.erzbir.mirai.numeron.utils.SqlUtil;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:32
 * <p>
 * 违禁词
 * </p>
 */
@Getter
public class IllegalList {
    public final static IllegalList INSTANCE = new IllegalList();

    static {
        String sql = """
                CREATE TABLE IF NOT EXISTS ILLEGALS(
                    KEY BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * FROM ILLEGALS";
        try {
            SqlUtil.listInit(sql, findAll, INSTANCE.illegal, "KEY", String.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private final HashSet<String> illegal = new HashSet<>();

    private boolean exist(String value) {
        String sql = "SELECT * FROM ILLEGALS WHERE KEY = '" + value + "'";
        ResultSet resultSet = SqlUtil.getResultSet(sql);
        try {
            return resultSet == null;
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

    private void addS(String value, Long id) {
        if (exist(value)) {
            return;
        }
        String sql = "INSERT INTO ILLEGALS(KEY, OP_ID, OP_TIME) VALUES(" + value + ", " + id + ", '" + LocalTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(String value) {
        if (!exist(value)) {
            return;
        }
        String sql = "DELETE FROM ILLEGALS WHERE KEY = " + value;
        SqlUtil.executeUpdateSQL(sql);
    }

    public boolean contains(String s) {
        return illegal.contains(s);
    }

    private void addD(String value) {
        illegal.add(value);
    }

    private void removeD(String value) {
        illegal.remove(value);
    }

    public void add(String value, Long id) {
        addD(value);
        new Thread(() -> addS(value, id)).start();
    }

    public void remove(String value) {
        removeD(value);
        new Thread(() -> removeS(value)).start();
    }

    public String query(String s) {
        if (s.equals("0")) {
            return toString();
        }
        if (contains(s)) {
            return "在黑名单中";
        }
        return "查无此人";
    }

    @Override
    public String toString() {
        return illegal.toString();
    }
}
