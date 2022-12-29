package com.erzbir.mirai.numeron.bot.terminalsend;

import com.erzbir.mirai.numeron.entity.NumeronBot;
import com.erzbir.mirai.numeron.handler.Plugin;
import com.erzbir.mirai.numeron.handler.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/12/13 16:44
 * [消息] [id] 发送
 */
@Plugin
public class TerminalScanner implements Runnable, PluginRegister {
    private final Object key = new Object();
    private Bot bot;

    @Override
    public void run() {
        Map<String, Contact> map = new HashMap<>();
        bot = NumeronBot.INSTANCE.getBot();
        bot.getGroups().forEach(e -> map.put(String.valueOf(e.getId()), e));
        bot.getBot().getFriends().forEach(e -> map.put(String.valueOf(e.getId()), e));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        synchronized (key) {
            while (true) {
                try {
                    String messages = bufferedReader.readLine();
                    String[] split = messages.split("\\s+");
                    if (split.length == 2) {
                        send(new PlainText(split[1]), Objects.requireNonNull(map.get(split[1])));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void send(Message message, Contact contact) {
        contact.sendMessage(message);
    }

    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.subscribeOnce(BotOnlineEvent.class, event -> {
                    new Thread(new TerminalScanner()).start();
                    this.bot = bot;
                }
        );
    }
}