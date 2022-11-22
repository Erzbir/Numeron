package com.erzbir.mirai.numeron.controller.factory;

import com.erzbir.mirai.numeron.controller.Action;
import com.erzbir.mirai.numeron.controller.WhiteListAction;
import com.erzbir.mirai.numeron.interfaces.ActionFactory;

/**
 * @author Erzbir
 * @Date: 2022/11/13 23:13
 */
public class WhiteListActionFactory implements ActionFactory {
    public static final WhiteListActionFactory INSTANCE = new WhiteListActionFactory();

    private WhiteListActionFactory() {
    }

    @Override
    public Action build() {
        return WhiteListAction.getINSTANCE();
    }
}
