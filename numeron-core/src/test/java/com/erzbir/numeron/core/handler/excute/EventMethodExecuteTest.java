package com.erzbir.numeron.core.handler.excute;


import com.erzbir.numeron.core.listener.EventMethodExecute;
import org.junit.jupiter.api.Test;

import java.util.Arrays;


/**
 * @author Erzbir
 * @Date 2023/8/13
 */
class EventMethodExecuteTest {

    @Test
    void testSplitMethodName() throws NoSuchMethodException {
        String[] strings = EventMethodExecute.INSTANCE.splitMethodName("com.erzbir.test.sayHello");
        System.out.println(Arrays.toString(strings));
    }
}