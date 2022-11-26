package com.erzbir.mirai.numeron.sql;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Erzbir
 * @Date: 2022/11/20 00:37
 * <p>
 * 数据库操作工具类, 目前的实现非常难以维护, 目标是通过获取注解的值来执行sql语句并注入进变量, 并且通过扫瞄实现了特定接口的类来为他们创建表
 * </p>
 */
public class SqlUtil {
    public static Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:data.sqlite");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private SqlUtil() {
    }
}
