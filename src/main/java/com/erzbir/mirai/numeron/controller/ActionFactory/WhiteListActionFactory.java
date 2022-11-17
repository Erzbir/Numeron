package com.erzbir.mirai.numeron.controller.ActionFactory;

import com.erzbir.mirai.numeron.Interface.Factory;

/**
 * @Author: Erzbir
 * @Date: 2022/11/13 23:13
 */
public class WhiteListActionFactory implements Factory {
    private static final WhiteListAction INSTANCE = new WhiteListAction();
    private static final WhiteListActionFactory INSTANCE2 = new WhiteListActionFactory();

    private WhiteListActionFactory() {
    }

    public static WhiteListActionFactory newInstance() {
        return INSTANCE2;
    }

    @Override
    public Action build() {
        return INSTANCE;
    }
}
