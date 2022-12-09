package com.erzbir.mirai.numeron.plugins.qqmanage.action;

import com.erzbir.mirai.numeron.configs.GlobalConfig;
import com.erzbir.mirai.numeron.enums.MessageRule;
import com.erzbir.mirai.numeron.enums.PermissionType;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.GroupMessage;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;

import java.util.HashMap;

/**
 * @author Erzbir
 * @Date: 2022/11/27 13:07
 */
@Plugin
@Listener
@SuppressWarnings("unused")
public class MemberLeaveAction implements PluginRegister {
    private final HashMap<Long, Boolean> isOn = new HashMap<>();

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        bot.getEventChannel().filter(f -> GlobalConfig.isOn).subscribeAlways(MemberLeaveEvent.class, event -> {
            if (isOn.get(event.getGroupId())) {
                event.getGroup().sendMessage(event.getMember().getNick() + " 离开了我们...");
            }
        });
    }

    @GroupMessage(text = "/leave\\s+?feedback\\s+?(true|false)", permission = PermissionType.WHITE, messageRule = MessageRule.REGEX)
    private void onOff(GroupMessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+?");
        isOn.put(event.getGroup().getId(), Boolean.parseBoolean(s[2]));
        event.getSubject().sendMessage(isOn.toString());
    }
}
