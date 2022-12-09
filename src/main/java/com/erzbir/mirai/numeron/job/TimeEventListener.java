package com.erzbir.mirai.numeron.job;

import com.erzbir.mirai.numeron.enums.FilterRule;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Erzbir
 * @Date: 2022/12/5 10:37
 */
@Listener
@SuppressWarnings("unused")
public class TimeEventListener {

    // time\s+?job\s+?(MessageAction|MuteAction|UnMuteAction)\s+?\S+?\s+?.*?\s+?enable

    @Message(text = "AllMessageTimeAction\\s+?(\\S+?)\\s+?(enable|disable)\\s*(\\S*)\\s*(.*)", messageRule = MessageRule.REGEX, permission = PermissionType.MASTER, filterRule = FilterRule.NORMAL)
    private void onMessage1(MessageEvent event) {
        Matcher matcher = Pattern.compile("AllMessageTimeAction\\s+?(\\S+?)\\s+?(enable|disable)\\s*(\\S*)\\s*?(.*)").matcher(event.getMessage().contentToString());
        String s1 = null;
        String s2 = "";
        String s3 = null;
        String s4 = null;
        if (matcher.matches()) {
            s1 = matcher.group(1);
            s2 = matcher.group(2);
            s3 = matcher.group(3);
            s4 = matcher.group(4);
        }
        switch (s2) {
            case "enable" -> {
                TimeJob task = TimeJob.createTask(s1, s4,
                        new AllMessageAction(new MessageChainBuilder().build().plus(s3), event.getSubject()));
                Jobs.add(task);
            }
            case "disable" -> Jobs.remove(AllMessageAction.class, s1);
        }
        GroupMuteTimeAction groupUnMuteTimeAction = new GroupMuteTimeAction();
        groupUnMuteTimeAction.mute(1);
    }


    // time\s+?job\s+?(MessageAction|MuteAction|UnMuteAction)\s+?\S+?\s+?.*?\s+?enable

    @Message(text = "asas", messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void onMessage3(MessageEvent event) {
        event.getSubject().sendMessage(Jobs.getString());
    }
}
