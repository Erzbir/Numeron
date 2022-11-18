package com.erzbir.mirai.numeron.test;

import com.erzbir.mirai.numeron.Annotation.GroupMessage;
import com.erzbir.mirai.numeron.Annotation.Listener;
import com.erzbir.mirai.numeron.Annotation.Message;
import com.erzbir.mirai.numeron.Annotation.UserMessage;
import com.erzbir.mirai.numeron.enums.MessageRule;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/11/18 20:17
 */
@Listener
public class Test {
    @GroupMessage (messageRule = MessageRule.REGEX, filter = true, text = "\\d+")
    public void regex(@NotNull GroupMessageEvent e) {
        e.getSubject().sendMessage("awa");
    }

    @UserMessage (text = "hi", filter = true)
    public void sayHello(@NotNull UserMessageEvent e) {
        e.getSubject().sendMessage("hi");
    }

    @Message (text = "晚安", filter = true)
    public void sayGoodNight(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("晚安");
    }
}
