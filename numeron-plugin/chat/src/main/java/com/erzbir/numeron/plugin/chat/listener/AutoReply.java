package com.erzbir.numeron.plugin.chat.listener;

import com.erzbir.numeron.annotation.*;
import com.erzbir.numeron.api.permission.PermissionType;
import com.erzbir.numeron.enums.MatchType;
import com.erzbir.numeron.plugin.chat.entity.AutoReplyData;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.event.events.UserMessageEvent;
import net.mamoe.mirai.message.data.PlainText;

/**
 * @author Erzbir
 * @Date: 2022/11/18 20:17
 * <br>
 * 消息处理注解使用示例
 */
@Listener
@Lazy
@SuppressWarnings("unused")
public class AutoReply {

    @Handler
    @Filter(value = "a", matchType = MatchType.TEXT_STARTS_WITH)
    private void reply(MessageEvent e) {
        String s = AutoReplyData.INSTANCE.getAnswer().get(e.getMessage().contentToString().replaceFirst("^a", ""));
        if (s != null) {
            e.getSubject().sendMessage(s);
        }
    }

    @Command(
            name = "自动回复",
            dec = "添加关键词回复",
            help = "/learn ques answer"
    )
    @Handler
    @Filter(value = "^/learn\\s+?.*?\\s+?.*", matchType = MatchType.REGEX_MATCHES)
    private void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        if (split.length < 3) {
            return;
        }
        AutoReplyData.INSTANCE.add(split[1], split[2], e.getSender().getId());
        e.getSubject().sendMessage("学会了");
    }

    @Command(
            name = "自动回复",
            dec = "删除关键词回复",
            help = "/forget ques"
    )
    @Handler
    @Filter(value = "^/forget\\s+\\S+", matchType = MatchType.REGEX_MATCHES)
    private void forget(MessageEvent e) {
        String s = e.getMessage().contentToString().replaceAll("\\s+", "");
        if (s.isEmpty()) {
            return;
        }
        AutoReplyData.INSTANCE.remove(s);
        e.getSubject().sendMessage("忘掉了");
    }


    // 测试
    @Handler
    @Filters({
            @Filter(value = "晚安", targets = @Targets(senders = 2978086497L))
    })
    private void sayGoodNight(MessageEvent e) {
        e.getSubject().sendMessage(new PlainText("晚安"));
    }

    @Handler
    @Filter("你好啊")
    private void sayH(MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }

    @Handler
    @Filters({
            @Filter(value = "我不好 哈哈", targets = @Targets(senders = 2978086497L), matchType = MatchType.TEXT_CONTAINS)
    })
    private void test(UserMessageEvent e) {
        e.getSubject().sendMessage("笑了");
    }

    @Handler
    @Permission(permission = PermissionType.ADMIN)
    @Filter(value = "^s\\d+", matchType = MatchType.REGEX_MATCHES)
    private void regex(UserMessageEvent e) {
        e.getSubject().sendMessage("awa");
    }

    @Handler
    @Permission(permission = PermissionType.WHITE)
    @Filter("hi")
    private void sayHello(UserMessageEvent e) {
        e.getSubject().sendMessage("hi");
    }

    @Handler
    @Permission(permission = PermissionType.EVERYONE)
    @Filter("小黑子")
    @RunBefore("com.erzbir.numeron.plugin.chat.listener.PInterrupt.print")
    private void sayZ(MessageEvent e) {
        e.getSubject().sendMessage("只因你太美");
    }
}
