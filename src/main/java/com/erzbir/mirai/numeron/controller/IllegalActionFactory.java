package com.erzbir.mirai.numeron.controller;

import com.erzbir.mirai.numeron.interfaces.ActionFactory;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:30
 */
public class IllegalActionFactory implements ActionFactory {
    public static final IllegalActionFactory INSTANCE = new IllegalActionFactory();
    private static final IllegalAction INSTANCE1 = new IllegalAction();

    private IllegalActionFactory() {
    }

    @Override
    public Action build() {
        return INSTANCE1;
    }
}