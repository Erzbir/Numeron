package com.erzbir.numeron.plugin.qqmanage.command;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.api.NumeronImpl;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.utils.ConfigCreateUtil;
import com.erzbir.numeron.utils.NumeronLogUtil;
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
    private static List<JsonElement> group = null;

    static {
        Gson gson = new Gson();
        String confFile = NumeronImpl.INSTANCE.getPluginWorkDir() + "qqmanage/config" + "/config.json";
        try {
            ConfigCreateUtil.createFile(confFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader in = new BufferedReader(new FileReader(confFile))) {
            JsonElement jsonElement = JsonParser.parseReader(in);
            if (!jsonElement.isJsonNull()) {
                group = jsonElement.getAsJsonObject().get("group").getAsJsonArray().asList();
            }
        } catch (IOException e) {
            NumeronLogUtil.logger.error("ERROR", e);
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
        group.forEach(t -> Objects.requireNonNull(event.getBot().getGroup(t.getAsLong())).getSettings().setMuteAll(true));
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
        group.forEach(t -> Objects.requireNonNull(event.getBot().getGroup(t.getAsLong())).getSettings().setMuteAll(false));
    }
}
