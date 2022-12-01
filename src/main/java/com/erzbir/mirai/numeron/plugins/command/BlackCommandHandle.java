package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.controller.BlackListAction;
import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import com.erzbir.mirai.numeron.processor.Command;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:49
 */
@Listener
@SuppressWarnings("unused")
public class BlackCommandHandle {

    @Command(name = "黑名单操作", dec = "拉入黑名单", help = "/ban user [@user] 或者 /ban user [qq]")
    @Message(text = "/ban user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void ban(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2].replaceAll("@", ""));
        GlobalConfig.whiteList.remove(id);
        BlackListAction.getInstance().add(id, null, event.getSender().getId());
        event.getSubject().sendMessage(id + " 添加到黑名单");
    }

    @Command(name = "黑名单操作", dec = "移出黑名单", help = "/noban user [@user] 或者 /noban user [qq]")
    @Message(text = "/noban user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void remove(MessageEvent event) {
        String[] split = event.getMessage().contentToString().split("\\s+");
        long id = Long.parseLong(split[2].replaceAll("@", ""));
        BlackListAction.getInstance().remove(id);
        event.getSubject().sendMessage(id + " 已移出黑名单");
    }

    @Command(name = "黑名单操作", dec = "查询黑名单", help = "/query black [qq]")
    @Message(text = "/query black\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    public void query(MessageEvent event) {
        event.getSubject().
                sendMessage(BlackListAction.getInstance()
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
