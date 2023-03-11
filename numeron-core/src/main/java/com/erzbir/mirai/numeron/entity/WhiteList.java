package com.erzbir.mirai.numeron.entity;

import com.erzbir.mirai.numeron.utils.SqlUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:38
 * <p>
 * 白名单
 * </p>
 */
public final class WhiteList {
    public final static WhiteList INSTANCE = new WhiteList();
    private final HashSet<Long> white = new HashSet<>();

    private WhiteList() {
        String sql = """
                CREATE TABLE IF NOT EXISTS WHITES(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * FROM WHITES";
        try {
            SqlUtil.listInit(sql, findAll, white, "ID", Long.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean contains(long id) {
        return white.contains(id);
    }

    private void addS(long value, long id) throws SQLException {
        String sql = "INSERT INTO WHITES(ID, OP_ID, OP_TIME) " +
                "VALUES(" + value + ", " + id + ", '" + LocalDateTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(long value) throws SQLException {
        String sql = "DELETE FROM WHITES WHERE ID = " + value;
        SqlUtil.executeUpdateSQL(sql);
    }


    private void addD(long value) {
        white.add(value);
    }

    private void removeD(long value) {
        white.remove(value);
    }

    public void add(long value, long id) {
        addD(value);
        new Thread(() -> {
            try {
                addS(value, id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void remove(long value) {
        removeD(value);
        new Thread(() -> {
            try {
                removeS(value);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public String query(long id) {
        if (id == 0) {
            return toString();
        }
        if (contains(id)) {
            return "在白名单中";
        }
        return "查无此人";
    }

    public HashSet<Long> getWhite() {
        return white;
    }

    @Override
    public String toString() {
        return white.toString();
    }
}
