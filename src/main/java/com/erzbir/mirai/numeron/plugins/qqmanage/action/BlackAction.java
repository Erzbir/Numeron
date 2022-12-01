package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import net.mamoe.mirai.event.events.GroupMessageEvent;

import java.util.Objects;

@Listener
@SuppressWarnings("unused")
public class BlackAction {

    @GroupMessage(text = ".*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.ALL)
    public void scan(GroupMessageEvent event) {
        if (GlobalConfig.blackList.contains(event.getSender().getId())) {
            Objects.requireNonNull(event.getGroup().get(event.getSender().getId())).kick("踢出黑名单用户", true);
        }
    }
}