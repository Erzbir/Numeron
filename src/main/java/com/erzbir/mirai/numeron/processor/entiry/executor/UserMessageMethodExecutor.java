package com.erzbir.mirai.numeron.processor.entiry.executor;

import com.erzbir.mirai.numeron.processor.entiry.excute.UserMessageMethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/28 01:00
 */
public class UserMessageMethodExecutor extends AbstractMethodExecutor {
    public static final UserMessageMethodExecutor INSTANCE = new UserMessageMethodExecutor();

    private UserMessageMethodExecutor() {
        super(new UserMessageMethodExecute());
    }
}
