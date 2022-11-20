package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.sql.SqlUtil;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:27
 */
public class BlackListAction extends Action {

    @Override
    public String add(Object id) {
        GlobalConfig.blackList.add((Long) id);
        SqlUtil.add((Long) id);
        return "添加成功";
    }

    @Override
    public String query(Object id) {
        if ((Long) id == 0L) {
            return GlobalConfig.blackList.toString();
        } else if (GlobalConfig.blackList.contains((Long) id)) {
            return "在黑名单中";
        }
        return "查无此人";
    }

    @Override
    public String remove(Object id) {
        GlobalConfig.blackList.remove((Long) id);
        SqlUtil.remove((Long) id);
        return "删除成功";
    }


}
