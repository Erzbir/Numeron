package com.erzbir.mirai.numeron.configs.entity;

import com.erzbir.mirai.numeron.utils.SqlUtil;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:11
 * <p>
 * 黑名单
 * </p>
 */

@Getter
public class BlackList {
    public final static BlackList INSTANCE = new BlackList();

    static {
        String sql = """
                CREATE TABLE IF NOT EXISTS BLACKS(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * from BLACKS";
        try {
            SqlUtil.listInit(sql, findAll, INSTANCE.black, "ID", Long.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private final HashSet<Long> black = new HashSet<>();

    private boolean exist(Long value) {
        String sql = "SELECT * FROM BLACKS WHERE ID = " + value;
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

    public boolean contains(Long id) {
        return black.contains(id);
    }

    private void addS(Long value, Long id) {
        if (exist(value)) {
            return;
        }
        String sql = "INSERT INTO BLACKS(ID, OP_ID, OP_TIME) " +
                "VALUES(" + value + ", " + id + ", '" + LocalTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(Long value) {
        if (!exist(value)) {
            return;
        }
        String sql = "DELETE FROM BLACKS WHERE ID = " + value;
        SqlUtil.executeUpdateSQL(sql);
    }


    private void addD(Long value) {
        black.add(value);
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

    public String query(Long id) {
        if (id == 0) {
            return toString();
        }
        if (contains(id)) {
            return "在黑名单中";
        }
        return "查无此人";
    }


    @Override
    public String toString() {
        return black.toString();
    }
}
