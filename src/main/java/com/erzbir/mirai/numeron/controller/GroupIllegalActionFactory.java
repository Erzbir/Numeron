package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.interfaces.ActionFactory;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:30
 */
public class GroupIllegalActionFactory implements ActionFactory {
    private static final GroupIllegalAction INSTANCE = new GroupIllegalAction();
    private static final GroupIllegalActionFactory INSTANCE2 = new GroupIllegalActionFactory();

    public GroupIllegalActionFactory() {
    }

    public static GroupIllegalActionFactory newInstance() {
        return INSTANCE2;
    }

    @Override
    public Action build() {
        return INSTANCE;
    }
}