package com.erzbir.mirai.numeron.plugins.command;

import com.erzbir.mirai.numeron.config.GlobalConfig;
import com.erzbir.mirai.numeron.controller.Action;
import com.erzbir.mirai.numeron.controller.factory.BlackListActionFactory;
import com.erzbir.mirai.numeron.controller.factory.WhiteListActionFactory;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/11/16 22:29
 * <p>
 * 插件示例
 * </p>
 */
@Plugin
@SuppressWarnings("unused")
public class CommandExecutor implements PluginRegister {
    @Override
    public void register(Bot bot, @NotNull EventChannel<BotEvent> channel) {
        channel.subscribeAlways(MessageEvent.class, event -> {
            String content = event.getMessage().contentToString();
            String[] command = content.split(" ");
            switch (command[0]) {
                case "#permit" -> {
                    if (event.getSender().getId() == GlobalConfig.master) {
                        if (command.length != 2) {
                            event.getSubject().sendMessage("use: /permit [userId]");
                            return;
                        }
                        long userId = Long.parseLong(command[1]);
                        Action action = WhiteListActionFactory.INSTANCE.build();
                        action.add(userId);
                        event.getSubject().sendMessage(userId + " was added to the whitelist");
                    }
                }
                case "#ban" -> {
                    if (event.getSender().getId() != GlobalConfig.master) {
                        event.getSubject().sendMessage("you don't have permission");
                        return;
                    }
                    if (command.length != 2) {
                        event.getSubject().sendMessage("use: /ban [userId]");
                        return;
                    }
                    long userId = Long.parseLong(command[1]);
                    BlackListActionFactory.INSTANCE.build().add(userId);
                    event.getSubject().sendMessage(userId + " was added to the blacklist");
                }
                case "#s" -> {
                    Action action = BlackListActionFactory.INSTANCE.build();
                    event.getSubject().sendMessage(action.query(Long.parseLong(command[1])));
                }
                case "#mute" -> {
                    if (GlobalConfig.whiteList.contains(event.getSender().getId())
                            || event.getSender().getId() == GlobalConfig.master) {
                        if (event instanceof GroupMessageEvent event1) {
                            if (command.length < 3) {
                                event.getSubject().sendMessage("use: /mute [time] [userId],[userId]");
                                return;
                            }
                            Group group = event1.getGroup();
                            int time = Integer.parseInt(command[1]);
                            String[] userIds = command[2].split(",");
                            if (group.getBotPermission().getLevel() < 1) {
                                event.getSubject().sendMessage("bot is not admin");
                                return;
                            }
                            for (String s : userIds) {
                                Objects.requireNonNull(group.get(Long.parseLong(s))).mute(time);
                            }
                        }
                    }
                }
                case "#unmute" -> {
                    if (GlobalConfig.whiteList.contains(event.getSender().getId())
                            || event.getSender().getId() == GlobalConfig.master) {
                        if (event instanceof GroupMessageEvent event1) {
                            if (command.length < 2) {
                                event.getSubject().sendMessage("use: /unmute [userId],[userId]");
                                return;
                            }
                            Group group = event1.getGroup();
                            String[] userIds = command[1].split(",");
                            if (group.getBotPermission().getLevel() < 1) {
                                event.getSubject().sendMessage("bot is not admin");
                                return;
                            }
                            for (String s : userIds) {
                                Objects.requireNonNull(group.get(Long.parseLong(s))).unmute();
                            }
                        }
                    }
                }
                case "#muteAll" -> {
                    if (GlobalConfig.whiteList.contains(event.getSender().getId())
                            || event.getSender().getId() == GlobalConfig.master) {
                        if (command.length < 2) {
                            event.getSubject().sendMessage("use: /muteAll [groupId],[groupId]");
                            return;
                        }
                        String[] groupIds = command[1].split(",");
                        for (String s : groupIds) {
                            Group group = event.getBot().getGroup(Long.parseLong(s));
                            if (group == null) {
                                continue;
                            }
                            if (group.getBotPermission().getLevel() < 1) {
                                event.getSubject().sendMessage("bot is not admin");
                                continue;
                            }
                            group.getSettings().setMuteAll(true);
                        }
                    }
                }
                case "#unmuteAll" -> {
                    if (GlobalConfig.whiteList.contains(event.getSender().getId())
                            || event.getSender().getId() == GlobalConfig.master) {
                        if (command.length < 2) {
                            event.getSubject().sendMessage("use: /unmuteAll [groupId],[groupId]");
                            return;
                        }
                        String[] groupIds = command[1].split(",");
                        for (String s : groupIds) {
                            Group group = event.getBot().getGroup(Long.parseLong(s));
                            if (group == null) {
                                continue;
                            }
                            if (group.getBotPermission().getLevel() < 1) {
                                event.getSubject().sendMessage("bot is not admin");
                                continue;
                            }
                            group.getSettings().setMuteAll(false);
                        }
                    }
                }
                case "#kick" -> {
                    if (GlobalConfig.whiteList.contains(event.getSender().getId())
                            || event.getSender().getId() == GlobalConfig.master) {
                        if (event instanceof GroupMessageEvent event1) {
                            if (command.length != 3) {
                                event.getSubject().sendMessage("use: /kick [userId] [always?]");
                                return;
                            }
                            if (event1.getGroup().getBotPermission().getLevel() < 1) {
                                event.getSubject().sendMessage("bot is not admin");
                                return;
                            }
                            if (!command[2].equals("false") && !command[2].equals("true")) {
                                event.getSubject().sendMessage("param 3 is Boolean");
                                return;
                            }
                            boolean bool = Boolean.parseBoolean(command[2]);
                            Objects.requireNonNull(event1.getGroup().get(Long.parseLong(command[1]))).kick("踢出本群", bool);

                        } else {
                            if (command.length != 4) {
                                event.getSubject().sendMessage("use: /kick [groupId] [userId] [always?]");
                                return;
                            }
                            long groupId = Long.parseLong(command[1]);
                            Group group = event.getBot().getGroup(groupId);
                            if (group == null) {
                                return;
                            }
                            if (group.getBotPermission().getLevel() < 1) {
                                event.getSubject().sendMessage("bot is not admin");
                                return;
                            }
                            if (!command[2].equals("false") && !command[2].equals("true")) {
                                event.getSubject().sendMessage("param 3 is Boolean");
                                return;
                            }
                            boolean bool = Boolean.parseBoolean(command[3]);
                            Objects.requireNonNull(group.get(Long.parseLong(command[2]))).kick("踢出本群", bool);
                        }
                    }
                }
                case "#info" -> event.getSubject().sendMessage(GlobalConfig.toStrings());
            }
        });
    }
}
