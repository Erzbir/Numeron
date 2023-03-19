package com.erzbir.numeron.core.entity;


import com.erzbir.numeron.core.utils.SqlUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:29
 * <p>
 * 开启机器人的群
 * </p>
 */

public final class GroupList {
    public final static GroupList INSTANCE = new GroupList();
    private final HashSet<Long> group = new HashSet<>();

    private GroupList() {
        String sql = """
                CREATE TABLE IF NOT EXISTS GROUPS(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * FROM GROUPS";
        try {
            SqlUtil.listInit(sql, findAll, group, "ID", Long.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean contains(long id) {
        return group.contains(id);
    }

    private void addS(long value, long id) throws SQLException {
        String sql = "INSERT INTO GROUPS(ID, OP_ID, OP_TIME) " +
                "VALUES(" + value + ", " + id + ", '" + LocalDateTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(long value) throws SQLException {
        String sql = "DELETE FROM GROUPS WHERE ID = " + value;
        SqlUtil.executeUpdateSQL(sql);
    }


    private void addD(long value) {
        group.add(value);
    }

    private void removeD(long value) {
        group.remove(value);
    }

    public void add(long value, Long id) {
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
        }).start();
    }

    public String query(long id) {
        if (id == 0) {
            return toString();
        }
        if (contains(id)) {
            return "授权中";
        }
        return "没有授权";
    }

    public HashSet<Long> getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return group.toString();
    }
}
