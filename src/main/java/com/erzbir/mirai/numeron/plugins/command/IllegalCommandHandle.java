package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.controller.factory.IllegalActionFactory;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:48
 */
@Listener
@SuppressWarnings("unused")
public class IllegalCommandHandle {

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

}
