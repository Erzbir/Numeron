package com.erzbir.numeron.core.inf.connection;

import com.erzbir.numeron.utils.NumeronLogUtil;
import lombok.Getter;

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
    @Getter
    private static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + "erzbirnumeron/config/data.sqlite");
        } catch (Exception e) {
            NumeronLogUtil.logger.error("ERROR", e);
            System.exit(-1);
        }
    }

    private SqlConnection() {
    }

}
