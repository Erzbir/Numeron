package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.annotation.litener.Listener;
import com.erzbir.mirai.numeron.annotation.massage.GroupMessage;
import com.erzbir.mirai.numeron.annotation.massage.Message;
import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.controller.factory.IllegalActionFactory;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

@Listener
public class IllegalAction {

    @Message(text = "/add illegal\\s+?.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void add(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        String s = split[2];
        IllegalActionFactory.INSTANCE.build().add(s);
        event.getSubject().sendMessage("违禁词添加成功");
    }

    @Message(text = "/remove illegal\\s+?.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void remove(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        String s = split[2];
        IllegalActionFactory.INSTANCE.build().remove(s);
        event.getSubject().sendMessage("违禁词删除成功");
    }

    @GroupMessage(text = ".*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.ALL)
    public void scan(GroupMessageEvent event) {
        if (GlobalConfig.illegalList.contains(event.getMessage().contentToString())) {
            event.getSender().mute(30000);
        }
    }
}