package com.erzbir.mirai.numeron.plugins.chat;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.listener.massage.UserMessage;
import com.erzbir.mirai.numeron.plugins.chat.entity.AutoReplyData;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Erzbir
 * @Date: 2022/11/18 20:17
 * <br>
 * 消息处理注解使用示例
 */
@Listener
@SuppressWarnings("unused")
public class AutoReply {

    @Message(messageRule = MessageRule.REGEX, text = ".*", permission = PermissionType.ALL, filterRule = FilterRule.BLACK)
    public void reply(MessageEvent e) {
        String s = AutoReplyData.INSTANCE.getAnswer(e.getMessage().contentToString());
        if (s != null) {
            e.getSubject().sendMessage(s);
        }
    }

    @Message(messageRule = MessageRule.REGEX, text = "/learn\\s+?.*?\\s+?.*", filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    public void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        AutoReplyData.INSTANCE.add(split[1], split[2]);
        e.getSubject().sendMessage("学会了");
    }

    @Message(messageRule = MessageRule.REGEX, text = "/forget\\s+?.*", filterRule = FilterRule.BLACK, permission = PermissionType.ALL)
    public void forget(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        AutoReplyData.INSTANCE.remove(split[1], split[2]);
        e.getSubject().sendMessage("忘掉了");
    }

    @UserMessage(text = "hi, 你好, 我不好, 哈哈", permission = PermissionType.ALL, filterRule = FilterRule.BLACK, messageRule = MessageRule.IN)
    public void test(UserMessageEvent e) {
        e.getSubject().sendMessage("笑了");
    }

    @GroupMessage(messageRule = MessageRule.REGEX, text = "s\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACK)
    public void regex(GroupMessageEvent e) {
        e.getSubject().sendMessage("awa");
    }

    @UserMessage(text = "hi", permission = PermissionType.ALL, filterRule = FilterRule.BLACK)
    public void sayHello(UserMessageEvent e) {
        e.getSubject().sendMessage("hi");
    }

    @Message(text = "晚安", permission = PermissionType.MASTER, filterRule = FilterRule.NORMAL)
    public void sayGoodNight(MessageEvent e) {
//        Image image = Contact.uploadImage(e.getSender(), new File("pix1001_4.jpg"));
        e.getSubject().sendMessage(new PlainText("晚安").plus(Image.fromId("{19D4A890-3381-943A-FD22-E5E78D106157}.jpg")));
    }

    @Message(text = "你好", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    public void sayH(MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }

    @GroupMessage(messageRule = MessageRule.CONTAINS, text = "小黑子", permission = PermissionType.ALL, filterRule = FilterRule.BLACK)
    public void sayZ(GroupMessageEvent e) {
        e.getSubject().sendMessage("只因你太美");
    }
}
