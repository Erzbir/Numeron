package com.erzbir.mirai.numeron.bot.qqmanage.command;

import com.erzbir.mirai.numeron.entity.WhiteList;
import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

/**
 * @author Erzbir
 * @Date: 2022/11/27 22:50
 * <p>
 * 白名单相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class WhiteCommandHandle {

    @Command(name = "白名单操作", dec = "添加白名单", help = "/permit user [@user] 或者 /permit user [qq]")
    @Message(text = "/permit user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void permit2(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2].replaceAll("@", ""));
        WhiteList.INSTANCE.remove(id);
        WhiteList.INSTANCE.add(id, event.getSender().getId());
        event.getSubject().sendMessage(id + " 已添加到白名单");
    }

    @Command(name = "白名单操作", dec = "移出白名单", help = "/unpermit user [@user] 或者 /unpermit user [qq]")
    @Message(text = "/unpermit user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void noPermit(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2].replaceAll("@", ""));
        WhiteList.INSTANCE.remove(id);
        event.getSubject().sendMessage(id + " 已移出白名单");
    }

    @Command(name = "白名单操作", dec = "查询白名单", help = "/query white [id]")
    @Message(text = "/query white\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void query(MessageEvent event) {
        event.getSubject().
                sendMessage(WhiteList.INSTANCE
                        .query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
