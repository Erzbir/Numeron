package com.erzbir.mirai.numeron.processor.entiry.executor;

import com.erzbir.mirai.numeron.processor.entiry.excute.MemberEventMethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/28 10:13
 */
public class MemberEventMethodExecutor extends AbstractMethodExecutor {
    public static final MemberEventMethodExecutor INSTANCE = new MemberEventMethodExecutor();

    private MemberEventMethodExecutor() {
        super(new MemberEventMethodExecute());
    }
}
