package com.erzbir.mirai.numeron.processor.entiry.executor;

import com.erzbir.mirai.numeron.processor.entiry.excute.UserEventMethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:14
 */
public class UserEventMethodExecutor extends AbstractMethodExecutor {
    public static final UserEventMethodExecutor INSTANCE = new UserEventMethodExecutor();

    private UserEventMethodExecutor() {
        super(new UserEventMethodExecute());
    }
}
