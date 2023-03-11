package com.erzbir.mirai.numeron.plugins.qqmanage.action;


import com.erzbir.mirai.numeron.utils.SqlUtil;

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

public final class IllegalList {
    public final static IllegalList INSTANCE = new IllegalList();
    private final HashSet<String> illegal = new HashSet<>();

    private IllegalList() {
        String sql = """
                CREATE TABLE IF NOT EXISTS ILLEGALS(
                    KEY BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * FROM ILLEGALS";
        try {
            SqlUtil.listInit(sql, findAll, illegal, "KEY", String.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void addS(String value, Long id) throws SQLException {
        String sql = "INSERT INTO ILLEGALS(KEY, OP_ID, OP_TIME) VALUES(" + value + ", " + id + ", '" + LocalTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(String value) throws SQLException {
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
        new Thread(() -> {
            try {
                addS(value, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void remove(String value) {
        removeD(value);
        new Thread(() -> {
            try {
                removeS(value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
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

    public HashSet<String> getIllegal() {
        return illegal;
    }

    @Override
    public String toString() {
        return illegal.toString();
    }
}
