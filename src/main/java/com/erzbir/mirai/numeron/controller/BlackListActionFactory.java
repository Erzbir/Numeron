package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.Interface.ActionFactory;

/**
 * @Author: Erzbir
 * @Date: 2022/11/13 17:35
 */
public class BlackListActionFactory implements ActionFactory {
    private static final BlackListAction INSTANCE = new BlackListAction();
    private static final BlackListActionFactory INSTANCE2 = new BlackListActionFactory();

    private BlackListActionFactory() {
    }

    public static BlackListActionFactory newInstance() {
        return INSTANCE2;
    }

    @Override
    public Action build() {
        return INSTANCE;
    }

}
