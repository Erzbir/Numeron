package com.erzbir.mirai.numeron.entity;


import com.erzbir.mirai.numeron.utils.SqlUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:11
 * <p>
 * 黑名单
 * </p>
 */

public final class BlackList {
    public final static BlackList INSTANCE = new BlackList();
    private final HashSet<Long> black = new HashSet<>();

    private BlackList() {
        String sql = """
                CREATE TABLE IF NOT EXISTS BLACKS(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * from BLACKS";
        try {
            SqlUtil.listInit(sql, findAll, black, "ID", Long.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean contains(long id) {
        return black.contains(id);
    }

    private void addS(long value, long id) throws SQLException {
        String sql = "INSERT INTO BLACKS(ID, OP_ID, OP_TIME) " +
                "VALUES(" + value + ", " + id + ", '" + LocalDateTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(long value) throws SQLException {
        String sql = "DELETE FROM BLACKS WHERE ID = " + value;
        SqlUtil.executeUpdateSQL(sql);
    }


    private void addD(long value) {
        black.add(value);
    }

    private void removeD(long value) {
        black.remove(value);
    }

    public void add(long value, long id) {
        addD(value);
        new Thread(() -> {
            try {
                addS(value, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void remove(long value) {
        removeD(value);
        new Thread(() -> {
            try {
                removeS(value);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public String query(long id) {
        if (id == 0) {
            return toString();
        }
        if (contains(id)) {
            return "在黑名单中";
        }
        return "查无此人";
    }

    public HashSet<Long> getBlack() {
        return black;
    }

    @Override
    public String toString() {
        return black.toString();
    }
}
