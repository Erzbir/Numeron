package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.entity.GroupList;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/22 00:42
 */
public class GroupListAction implements Action {
    private static GroupListAction INSTANCE;

    private GroupListAction() {

    }

    public static GroupListAction getInstance() {
        return Objects.requireNonNullElseGet(INSTANCE, () -> INSTANCE = new GroupListAction());
    }

    @Override
    public void add(Object id, String name, Long op_id) {
        GroupList.INSTANCE.add((Long) id, op_id);
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
