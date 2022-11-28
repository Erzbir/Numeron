package com.erzbir.mirai.numeron.processor.entiry.executor;

import com.erzbir.mirai.numeron.processor.entiry.excute.MessageMethodExecute;

/**
 * @author Erzbir
 * @Date: 2022/11/28 00:57
 */
public class MessageMethodExecutor extends AbstractMethodExecutor {
    public static final MessageMethodExecutor INSTANCE = new MessageMethodExecutor();

    private MessageMethodExecutor() {
        super(new MessageMethodExecute());
    }
}
