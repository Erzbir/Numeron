package com.erzbir.numeron.plugin.chat.listner;

import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.core.listener.massage.GroupMessage;
import com.erzbir.numeron.core.listener.massage.Message;
import com.erzbir.numeron.core.listener.massage.UserMessage;
import com.erzbir.numeron.plugin.chat.entity.AutoReplyData;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.PlainText;

import java.sql.SQLException;

/**
 * @author Erzbir
 * @Date: 2022/11/18 20:17
 * <br>
 * 消息处理注解使用示例
 */
@Listener
@SuppressWarnings("unused")
public class AutoReply {

    @Message(
            messageRule = MessageRule.REGEX,
            text = ".*", permission = PermissionType.ALL,
            filterRule = FilterRule.BLACK
    )
    private void reply(MessageEvent e) {
        String s = AutoReplyData.INSTANCE.getAnswer(e.getMessage().contentToString());
        if (s != null) {
            e.getSubject().sendMessage(s);
        }
    }

    @Command(
            name = "自动回复",
            dec = "添加关键词回复",
            help = "/learn ques answer",
            permission = PermissionType.ALL
    )
    @Message(
            messageRule = MessageRule.REGEX,
            text = "^/learn\\s+?.*?\\s+?.*",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        AutoReplyData.INSTANCE.add(split[1], split[2], e.getSender().getId());
        e.getSubject().sendMessage("学会了");
    }

    @Command(
            name = "自动回复",
            dec = "删除关键词回复",
            help = "/forget ques",
            permission = PermissionType.ALL
    )
    @Message(
            messageRule = MessageRule.REGEX,
            text = "^/forget\\s+?.*",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void forget(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        try {
            AutoReplyData.INSTANCE.remove(split[1], split[2]);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        e.getSubject().sendMessage("忘掉了");
    }


    // 测试
    @Message(
            text = "晚安",
            permission = PermissionType.MASTER,
            filterRule = FilterRule.NORMAL
    )
    private void sayGoodNight(MessageEvent e) {
//        Image image = Contact.uploadImage(e.getSender(), new File("pix1001_4.jpg"));
        e.getSubject().sendMessage(new PlainText("晚安").plus(Image.fromId("{19D4A890-3381-943A-FD22-E5E78D106157}.jpg")));
    }

    @Message(
            text = "你好啊",
            permission = PermissionType.MASTER,
            filterRule = FilterRule.NONE
    )
    private void sayH(MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }

    @UserMessage(
            text = "hi, 你好, 我不好, 哈哈",
            permission = PermissionType.ALL,
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.IN
    )
    private void test(UserMessageEvent e) {
        e.getSubject().sendMessage("笑了");
    }

    @UserMessage(
            messageRule = MessageRule.REGEX,
            text = "s\\d+",
            permission = PermissionType.ALL,
            filterRule = FilterRule.BLACK
    )
    private void regex(UserMessageEvent e) {
        e.getSubject().sendMessage("awa");
    }

    @UserMessage(
            text = "hi",
            permission = PermissionType.ALL,
            filterRule = FilterRule.BLACK
    )
    private void sayHello(UserMessageEvent e) {
        e.getSubject().sendMessage("hi");
    }

    @GroupMessage(
            messageRule = MessageRule.EQUAL,
            text = "小黑子",
            permission = PermissionType.ADMIN,
            filterRule = FilterRule.BLACK
    )
    private void sayZ(GroupMessageEvent e) {
        e.getSubject().sendMessage("只因你太美");
    }
}
