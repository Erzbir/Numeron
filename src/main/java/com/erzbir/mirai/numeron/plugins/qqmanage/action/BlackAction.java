package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.annotation.litener.Listener;
import com.erzbir.mirai.numeron.annotation.massage.GroupMessage;
import com.erzbir.mirai.numeron.annotation.massage.Message;
import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.controller.factory.BlackListActionFactory;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;

import java.util.Objects;

@Listener
public class BlackAction {

    @Message(text = "/ban user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void ban(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        GlobalConfig.whiteList.remove(id);
        BlackListActionFactory.INSTANCE.build().add(id);
        event.getSubject().sendMessage(id + " 添加到黑名单");
    }

    @Message(text = "/noban user\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void remove(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2]);
        BlackListActionFactory.INSTANCE.build().remove(id);
        event.getSubject().sendMessage(id + " 已移出黑名单");
    }

    @GroupMessage(text = ".*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.ALL)
    public void scan(GroupMessageEvent event) {
        if (GlobalConfig.blackList.contains(event.getSender().getId())) {
            Objects.requireNonNull(event.getGroup().get(event.getSender().getId())).kick("踢出黑名单用户", true);
        }
    }
}