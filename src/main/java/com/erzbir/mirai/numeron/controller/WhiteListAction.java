package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.entity.WhiteList;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/13 23:13
 */
public class WhiteListAction extends Action {
    private static WhiteListAction INSTANCE;

    private WhiteListAction() {

    }

    public static WhiteListAction getINSTANCE() {
        return Objects.requireNonNullElseGet(INSTANCE, () -> INSTANCE = new WhiteListAction());
    }

    @Override
    public String add(Object id) {
        WhiteList.INSTANCE.add((Long) id);
        return "添加成功";
    }

    @Override
    public String query(Object id) {
        if ((Long) id == 0L) {
            return GlobalConfig.blackList.toString();
        }
        if (GlobalConfig.whiteList.contains((Long) id)) {
            return "在黑名单中";
        }
        return "查无此人";
    }

    @Override
    public String remove(Object id) {
        WhiteList.INSTANCE.remove((Long) id);
        return "删除成功";
    }
}
