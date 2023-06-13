package com.erzbir.numeron.plugin.qqmanage;

import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.utils.NumeronLogUtil;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Erzbir
 * @Date: 2022/11/20 00:37
 * <p>
 * 数据库链接
 * </p>
 */
public final class SqlConnection {
    private static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + NumeronImpl.INSTANCE.getPluginWorkDir() + "qqmanage/config/data.sqlite");
        } catch (Exception e) {
            NumeronLogUtil.logger.error(e);
            System.exit(-1);
        }
    }

    private SqlConnection() {
    }

    public static Connection getConnection() {
        return connection;
    }
}
