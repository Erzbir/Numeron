package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.controller.IllegalManager;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.processor.Command;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:48
 */
@Listener
@SuppressWarnings("unused")
public class IllegalCommandHandle {

    @Command(name = "违禁词操作", dec = "添加违禁词", help = "/add illegal [key]")
    @Message(text = "/add illegal\\s+?.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void add(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        String s = split[2];
        IllegalManager.getInstance().add(s, null, event.getSender().getId());
        event.getSubject().sendMessage("违禁词添加成功");
    }

    @Command(name = "违禁词操作", dec = "删除违禁词", help = "/remove illegal [key]")
    @Message(text = "/remove illegal\\s+?.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void remove(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        String s = split[2];
        IllegalManager.getInstance().remove(s);
        event.getSubject().sendMessage("违禁词删除成功");
    }

    @Command(name = "违禁词操作", dec = "查询违禁词", help = "/query illegal [key]")
    @Message(text = "/query illegal\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void query(MessageEvent event) {
        event.getSubject().
                sendMessage(IllegalManager.getInstance()
                        .query(event.getMessage().contentToString().split("\\s+")[2]));
    }
}
