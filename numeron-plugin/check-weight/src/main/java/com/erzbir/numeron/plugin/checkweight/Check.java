package com.erzbir.numeron.plugin.checkweight;


import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Filter;
import com.erzbir.numeron.annotation.Handler;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.api.filter.enums.MatchType;
import com.erzbir.numeron.menu.Menu;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.mamoe.mirai.event.events.MessageEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author Erzbir
 * @Date: 2023/3/8 18:43
 */
@Listener
@Menu(name = "查权重")
@SuppressWarnings("unused")
public class Check {
    private static final String API = "http://tfapi.top/API/qqqz.php?type=json&qq=";

    @Command(
            name = "查权重",
            dec = "查权重 [qq]或者[@xx]或者不写"
    )
    @Handler
    @Filter(value = "^查权重\\s*(@*\\d+)*", matchType = MatchType.REGEX_MATCHES)
    private void check(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^查权重(\\s*@*)*", "");
        if (!s.isEmpty()) {
            event.getSubject().sendMessage(getResult(s));
        } else {
            event.getSubject().sendMessage(getResult(String.valueOf(event.getSender().getId())));
        }
    }

    private String getResult(String id) {
        try (InputStream in = new URL(API + Long.parseLong(id)).openStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    private String parseResult(String result) {
        JsonElement jsonElement = JsonParser.parseString(result);
        return jsonElement.getAsJsonObject().get("msg").getAsString();
    }
}
