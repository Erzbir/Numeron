package com.erzbir.mirai.numeron.controller.factory;

import com.erzbir.mirai.numeron.controller.Action;
import com.erzbir.mirai.numeron.controller.GroupListAction;
import com.erzbir.mirai.numeron.interfaces.ActionFactory;

/**
 * @author Erzbir
 * @Date: 2022/11/22 00:53
 */
public class GroupListActionFactory implements ActionFactory {
    public static final GroupListActionFactory INSTANCE = new GroupListActionFactory();

    private GroupListActionFactory() {

    }

    @Override
    public Action build() {
        return GroupListAction.getINSTANCE();
    }
}
