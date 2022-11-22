package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.sql.SqlUtil;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:30
 */
public class IllegalAction extends Action {

    private static IllegalAction INSTANCE;

    private IllegalAction() {

    }

    public static IllegalAction getINSTANCE() {
        return Objects.requireNonNullElseGet(INSTANCE, () -> INSTANCE = new IllegalAction());
    }

    @Override
    public String add(Object s) {
        GlobalConfig.illegalList.add((String) s);
        SqlUtil.add((String) s);
        return "添加成功";
    }

    @Override
    public String query(Object s) {
        if (s.equals("all")) {
            return GlobalConfig.blackList.toString();
        }
        if (GlobalConfig.illegalList.contains((String) s)) {
            return "在黑名单中";
        }
        return "查无此人";
    }

    @Override
    public String remove(Object s) {
        GlobalConfig.illegalList.remove((String) s);
        SqlUtil.remove((String) s);
        return "删除成功";
    }
}