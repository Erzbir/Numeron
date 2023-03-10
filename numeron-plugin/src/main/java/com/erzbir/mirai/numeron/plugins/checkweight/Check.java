package com.erzbir.mirai.numeron.plugins.checkweight;

import com.erzbir.mirai.numeron.filter.message.MessageRule;
import com.erzbir.mirai.numeron.filter.permission.PermissionType;
import com.erzbir.mirai.numeron.filter.rule.FilterRule;
import com.erzbir.mirai.numeron.handler.Command;
import com.erzbir.mirai.numeron.listener.Listener;
import com.erzbir.mirai.numeron.listener.massage.Message;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Erzbir
 * @Date: 2023/3/8 18:43
 */
@Listener
public class Check {
    private static final String API = "http://tfapi.top/API/qqqz.php?type=json&qq=";

    @Command(
            name = "查权重",
            dec = "查权重 [qq]",
            permission = PermissionType.ALL
    )
    @Message(
            text = "^查权重\\s+?\\d*",
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.ALL
    )
    private void check(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^查权重\\s+?", "");
        event.getSubject().sendMessage(getResult(s));
    }

    private String getResult(String id) {
        try (InputStream in = new URL(API + id).openStream()) {
            return new String(in.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
