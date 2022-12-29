package com.erzbir.mirai.numeron.bot.qqmanage.command;


import com.erzbir.mirai.numeron.entity.BlackList;
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
 * @Date: 2022/11/27 22:49
 * <p>
 * 黑名单的相关命令
 * </p>
 */
@Listener
@SuppressWarnings("unused")
public class BlackCommandHandle {

    @Command(name = "黑名单操作", dec = "拉入黑名单", help = "/ban user [@user] 或者 /ban user [qq]")
    @Message(text = "/ban user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void ban(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2].replaceAll("@", ""));
        WhiteList.INSTANCE.remove(id);
        WhiteList.INSTANCE.add(id, event.getSender().getId());
        event.getSubject().sendMessage(id + " 添加到黑名单");
    }

    @Command(name = "黑名单操作", dec = "移出黑名单", help = "/noban user [@user] 或者 /noban user [qq]")
    @Message(text = "/noban user\\s+\\.*", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void remove(MessageEvent event) {
        long id = Long.parseLong(event.getMessage().contentToString().split("\\s+")[2].replaceAll("@", ""));
        WhiteList.INSTANCE.remove(id);
        event.getSubject().sendMessage(id + " 已移出黑名单");
    }

    @Command(name = "黑名单操作", dec = "查询黑名单", help = "/query black [qq]")
    @Message(text = "/query black\\s+\\d+", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void query(MessageEvent event) {
        event.getSubject().sendMessage(BlackList.INSTANCE.query(Long.parseLong(event.getMessage().contentToString().split("\\s+")[2])));
    }
}
