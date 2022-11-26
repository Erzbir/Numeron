package com.erzbir.mirai.numeron.controller.factory;

import com.erzbir.mirai.numeron.controller.Action;
import com.erzbir.mirai.numeron.controller.IllegalAction;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:30
 */
public class IllegalActionFactory implements ActionFactory {
    public static final IllegalActionFactory INSTANCE = new IllegalActionFactory();

    private IllegalActionFactory() {
    }

    @Override
    public Action build() {
        return IllegalAction.getINSTANCE();
    }
}