package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.config.GlobalConfig;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:27
 */
public class BlackListAction extends Action {

    @Override
    public void add(long id) {
        GlobalConfig.blackList.add(id);
    }

    @Override
    public String query(long id) {
        if (id == 0) {
            return GlobalConfig.blackList.toString();
        }
        if (GlobalConfig.whiteList.contains(id)) {
            return "在黑名单中";
        }
        return "";
    }

    @Override
    public void remove(long id) {
        GlobalConfig.blackList.remove(id);
    }


}
