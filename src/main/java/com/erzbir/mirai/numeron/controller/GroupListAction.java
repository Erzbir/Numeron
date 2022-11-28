package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.entity.GroupList;

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
    public void add(Object id) {
        GroupList.INSTANCE.add((Long) id);
    }

    @Override
    public String query(Object id) {
        if ((Long) id == 0L) {
            return GlobalConfig.groupList.toString();
        } else if (GlobalConfig.groupList.contains((Long) id)) {
            return "授权中";
        }
        return "没有授权";
    }

    @Override
    public void remove(Object id) {
        GroupList.INSTANCE.remove((Long) id);
    }
}
