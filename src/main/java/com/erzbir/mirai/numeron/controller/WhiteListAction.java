package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.sql.SqlUtil;

/**
 * @author Erzbir
 * @Date: 2022/11/13 23:13
 */
public class WhiteListAction extends Action {
    @Override
    public String add(Object id) {
        GlobalConfig.whiteList.add((Long) id);
        SqlUtil.add((Long) id, "whiteLit");
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
        GlobalConfig.whiteList.remove((Long) id);
        SqlUtil.remove((Long) id, "whiteList");
        return "删除成功";
    }
}
