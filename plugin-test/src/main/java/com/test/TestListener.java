package com.test;

import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.test.Say;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2023/4/29 22:11
 */
@Listener
public class TestListener {
    @Message(
            text = "plugin-test1",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void test(MessageEvent event) {
        event.getSubject().sendMessage(Main.INSTANCE.getDescription().getName() + new Say().say() + "! " + "'所有消息 权限为 MASTER '测试成功");
    }

    @Message(
            text = "plugin-test2",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.MASTER
    )
    private void test2(GroupMessageEvent event) {
        event.getSubject().sendMessage(Main.INSTANCE.getDescription().getName() + new Say().say() + "! " + "'群消息 权限为 MASTER '测试成功");
    }

    @Message(
            text = "plugin-test3",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void test3(MessageEvent event) {
        event.getSubject().sendMessage(Main.INSTANCE.getDescription().getName() + new Say().say() + "! " + "'所有消息 权限为 ALL '测试成功");
    }
}
