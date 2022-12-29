package com.erzbir.mirai.numeron.bot.qqmanage.command;


import com.erzbir.mirai.numeron.bot.qqmanage.action.IllegalList;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:48
 * <p>
 * 违禁词列表相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class IllegalCommandHandle {

    @Command(name = "违禁词操作", dec = "添加违禁词", help = "/add illegal [key]")
    @Message(text = "/add illegal\\s+?.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void add(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        String s = split[2];
        IllegalList.INSTANCE.add(s, event.getSender().getId());
        event.getSubject().sendMessage("违禁词添加成功");
    }

    @Command(name = "违禁词操作", dec = "删除违禁词", help = "/remove illegal [key]")
    @Message(text = "/remove illegal\\s+?.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void remove(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        String s = split[2];
        IllegalList.INSTANCE.remove(s);
        event.getSubject().sendMessage("违禁词删除成功");
    }

    @Command(name = "违禁词操作", dec = "查询违禁词", help = "/query illegal [key]")
    @Message(text = "/query illegal\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(IllegalList.INSTANCE
                        .query(event.getMessage().contentToString().split("\\s+")[2]));
    }
}
