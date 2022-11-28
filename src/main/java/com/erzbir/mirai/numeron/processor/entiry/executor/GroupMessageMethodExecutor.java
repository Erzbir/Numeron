package com.erzbir.mirai.numeron.processor.entiry.executor;

import com.erzbir.mirai.numeron.processor.entiry.excute.GroupMessageMethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/28 00:59
 */
public class GroupMessageMethodExecutor extends AbstractMethodExecutor {
    public static final GroupMessageMethodExecutor INSTANCE = new GroupMessageMethodExecutor();
    private GroupMessageMethodExecutor() {
        super(new GroupMessageMethodExecute());
    }

}
