package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.core.entity.NumeronBot;
import com.erzbir.numeron.core.filter.message.MessageRule;
import com.erzbir.numeron.core.filter.permission.PermissionType;
import com.erzbir.numeron.core.filter.rule.FilterRule;
import com.erzbir.numeron.core.handler.Command;
import com.erzbir.numeron.core.handler.Message;
import com.erzbir.numeron.core.listener.Listener;
import com.erzbir.numeron.core.utils.ConfigCreateUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.mamoe.mirai.event.events.UserMessageEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2023/2/22 23:19
 */
@Listener
@SuppressWarnings("unused")
public class FastMute {
    private final static List<JsonElement> group;

    static {
        Gson gson = new Gson();
        String confFile = NumeronBot.INSTANCE.getFolder() + "plugin-configs/qqmanage" + "/config.json";
        try {
            ConfigCreateUtil.createFile(confFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader in = new BufferedReader(new FileReader(confFile))) {
            List<JsonElement> group1 = JsonParser.parseReader(in).getAsJsonObject().get("group").getAsJsonArray().asList();
            group = group1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Command(
            name = "打卡解/禁言",
            dec = "打卡禁言",
            help = "#打卡禁言",
            permission = PermissionType.ALL
    )
    @Message(
            text = "#打卡禁言",
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.EQUAL,
            permission = PermissionType.ALL
    )
    private void fastMute(UserMessageEvent event) {
        group.forEach(t -> Objects.requireNonNull(NumeronBot.INSTANCE.getBot().getGroup(t.getAsLong())).getSettings().setMuteAll(true));
    }

    @Command(
            name = "打卡解/禁言",
            dec = "打卡解禁",
            help = "#打卡禁言",
            permission = PermissionType.ALL
    )
    @Message(
            text = "#打卡解禁",
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.EQUAL,
            permission = PermissionType.ALL
    )
    private void fastUnMute(UserMessageEvent event) {
        group.forEach(t -> Objects.requireNonNull(NumeronBot.INSTANCE.getBot().getGroup(t.getAsLong())).getSettings().setMuteAll(false));
    }
}
