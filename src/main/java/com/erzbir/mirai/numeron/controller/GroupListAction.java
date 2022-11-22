package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.sql.SqlUtil;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/22 00:42
 */
public class GroupListAction extends Action {
    private static GroupListAction INSTANCE;

    private GroupListAction() {

    }

    public static GroupListAction getINSTANCE() {
        return Objects.requireNonNullElseGet(INSTANCE, () -> INSTANCE = new GroupListAction());
    }

    @Override
    public String add(Object id) {
        GlobalConfig.groupList.add((Long) id);
        SqlUtil.add((Long) id);
        return "授权成功";
    }

    @Override
    public String query(Object id) {
        if ((Long) id == 0L) {
            return GlobalConfig.groupList.toString();
        } else if (GlobalConfig.groupList.contains((Long) id)) {
            return "授权中";
        }
        return "查无此人";
    }

    @Override
    public String remove(Object id) {
        GlobalConfig.groupList.remove((Long) id);
        SqlUtil.remove((Long) id);
        return "取消授权成功";
    }
}
