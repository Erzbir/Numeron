package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.entity.WhiteList;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/13 23:13
 */
public class WhiteListAction implements Action {
    private static WhiteListAction INSTANCE;

    private WhiteListAction() {

    }

    public static WhiteListAction getInstance() {
        return Objects.requireNonNullElseGet(INSTANCE, () -> INSTANCE = new WhiteListAction());
    }

    @Override
    public void add(Object id, String name, Long op_id) {
        WhiteList.INSTANCE.add((Long) id, op_id);
    }

    @Override
    public String query(Object id) {
        if ((Long) id == 0L) {
            return GlobalConfig.whiteList.toString();
        }
        if (GlobalConfig.whiteList.contains((Long) id)) {
            return "在白名单中";
        }
        return "查无此人";
    }

    @Override
    public void remove(Object id) {
        WhiteList.INSTANCE.remove((Long) id);
    }
}
