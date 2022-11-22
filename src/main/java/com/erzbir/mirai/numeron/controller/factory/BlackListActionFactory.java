package com.erzbir.mirai.numeron.controller.factory;

import com.erzbir.mirai.numeron.controller.Action;
import com.erzbir.mirai.numeron.controller.BlackListAction;
import com.erzbir.mirai.numeron.interfaces.ActionFactory;

/**
 * @author Erzbir
 * @Date: 2022/11/13 17:35
 */
public class BlackListActionFactory implements ActionFactory {
    public static final BlackListActionFactory INSTANCE = new BlackListActionFactory();

    private BlackListActionFactory() {
    }

    @Override
    public Action build() {
        return BlackListAction.getInstance();
    }

}
