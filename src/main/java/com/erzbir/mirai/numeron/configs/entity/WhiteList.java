package com.erzbir.mirai.numeron.configs.entity;

import com.erzbir.mirai.numeron.utils.SqlUtil;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashSet;

/**
 * @author Erzbir
 * @Date: 2022/11/26 13:38
 * <p>
 * 白名单
 * </p>
 */
@Getter
public class WhiteList {
    public final static WhiteList INSTANCE = new WhiteList();

    static {
        String sql = """
                CREATE TABLE IF NOT EXISTS WHITES(
                    ID BIGINT PRIMARY KEY NOT NULL,
                    OP_ID BIGINT NOT NULL,
                    OP_TIME TEXT NOT NULL
                    )
                """;
        String findAll = "SELECT * FROM WHITES";
        try {
            SqlUtil.listInit(sql, findAll, INSTANCE.white, "ID", Long.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private final HashSet<Long> white = new HashSet<>();

    public boolean contains(Long id) {
        return white.contains(id);
    }

    private boolean exist(Long value) {
        String sql = "SELECT * FROM WHITES WHERE ID = " + value;
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

    private void addS(Long value, Long id) {
        if (exist(value)) {
            return;
        }

        String sql = "INSERT INTO WHITES(ID, OP_ID, OP_TIME) " +
                "VALUES(" + value + ", " + id + ", '" + LocalTime.now() + "' " + ")";
        SqlUtil.executeUpdateSQL(sql);
    }

    private void removeS(Long value) {
        if (!exist(value)) {
            return;
        }
        String sql = "DELETE FROM WHITES WHERE ID = " + value;
        SqlUtil.executeUpdateSQL(sql);
    }


    private void addD(Long value) {
        white.add(value);
    }

    private void removeD(Long value) {
        white.remove(value);
    }

    public void add(Long value, Long id) {
        addD(value);
        new Thread(() -> addS(value, id)).start();
    }

    public void remove(Long value) {
        removeD(value);
        new Thread(() -> removeS(value)).start();
    }

    public String query(Long id) {
        if (id == 0) {
            return toString();
        }
        if (contains(id)) {
            return "在白名单中";
        }
        return "查无此人";
    }

    @Override
    public String toString() {
        return white.toString();
    }
}
