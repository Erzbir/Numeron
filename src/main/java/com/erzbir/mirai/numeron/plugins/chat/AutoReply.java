package com.erzbir.mirai.numeron.plugins.chat;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.litener.Listener;
import com.erzbir.mirai.numeron.massage.GroupMessage;
import com.erzbir.mirai.numeron.massage.Message;
import com.erzbir.mirai.numeron.massage.UserMessage;
import com.erzbir.mirai.numeron.plugins.chat.entity.AutoReplyData;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Erzbir
 * @Date: 2022/11/18 20:17
 * <br>
 * 消息处理注解使用示例
 */
@Listener
public class AutoReply {

    @Message(messageRule = MessageRule.REGEX, text = ".*", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    public void reply(@NotNull MessageEvent e) {
        String s = AutoReplyData.INSTANCE.getAnswer(e.getMessage().contentToString());
        if (s != null) {
            e.getSubject().sendMessage(s);
        }
    }

    @Message(messageRule = MessageRule.REGEX, text = "/learn\\s+?.*?\\s+?.*", filterRule = FilterRule.BLACKLIST, permission = PermissionType.ALL)
    public void learn(@NotNull MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        AutoReplyData.INSTANCE.add(split[1], split[2]);
        e.getSubject().sendMessage("学会了");
    }

    @GroupMessage(messageRule = MessageRule.REGEX, text = "s\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    public void regex(@NotNull GroupMessageEvent e) {
        e.getSubject().sendMessage("awa");
    }

    @UserMessage(text = "hi", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    public void sayHello(@NotNull UserMessageEvent e) {
        e.getSubject().sendMessage("hi");
    }

    @Message(text = "晚安", permission = PermissionType.ALL, filterRule = FilterRule.NORMAL)
    public void sayGoodNight(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("晚安");
    }

    @Message(text = "你好", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    public void sayH(@NotNull MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }

    @GroupMessage(messageRule = MessageRule.CONTAINS, text = "小黑子", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
    public void sayZ(@NotNull GroupMessageEvent e) {
        e.getSubject().sendMessage("只因你太美");
    }
}
