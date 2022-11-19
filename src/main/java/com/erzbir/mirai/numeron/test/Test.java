package com.erzbir.mirai.numeron.test;

import com.erzbir.mirai.numeron.annotation.GroupMessage;
import com.erzbir.mirai.numeron.annotation.Listener;
import com.erzbir.mirai.numeron.annotation.Message;
import com.erzbir.mirai.numeron.annotation.UserMessage;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
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
    @GroupMessage (messageRule = MessageRule.REGEX, text = "\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    public void regex(@NotNull GroupMessageEvent e) {
        e.getSubject().sendMessage("awa");
    }

    @UserMessage (text = "hi", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    public void sayHello(@NotNull UserMessageEvent e) {
        e.getSubject().sendMessage("hi");
    }

    @Message (text = "晚安", permission = PermissionType.MASTER, filterRule = FilterRule.NORMAL)
    public void sayGoodNight(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("晚安");
    }

    @Message (text = "你好", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    public void sayH(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }
}
