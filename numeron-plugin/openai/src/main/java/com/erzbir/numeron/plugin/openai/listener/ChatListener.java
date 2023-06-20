package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.openai.Conversation;
import com.erzbir.numeron.plugin.openai.OpenAiServiceImpl;
import com.erzbir.numeron.plugin.openai.Role;
import com.erzbir.numeron.plugin.openai.config.ChatConfig;
import com.erzbir.numeron.plugin.openai.config.OpenAiConfig;
import com.erzbir.numeron.plugin.openai.config.RoleConfig;
import com.erzbir.numeron.utils.ConfigWriteException;
import com.erzbir.numeron.utils.NumeronLogUtil;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.ForwardMessageBuilder;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import retrofit2.HttpException;

import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/19 21:58
 */
@Listener
@Menu(name = "openai")
@SuppressWarnings("unused")
public class ChatListener {
    private final ChatConfig CHAT_CONFIG = ChatConfig.getInstance();
    private final RoleConfig ROLE_CONFIG = RoleConfig.getInstance();
    private Conversation CONVERSATION = new Conversation(OpenAiConfig.getInstance().getLimit());

    @Command(
            name = "OpenAI",
            dec = "设定人设",
            help = "/r 1",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/r\\s+\\d+",
            permission = PermissionType.ALL
    )
    private void setRole(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/r\\s+", "");
        int index = Integer.parseInt(s);
        CONVERSATION.add(0, new ChatMessage("system", ROLE_CONFIG.getRole(index).getPrompt()));
    }

    @Command(
            name = "OpenAI",
            dec = "增加人设",
            help = "/r -a [name] -n [prompt]",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/r\\s+?-a\\s+?\\S+?-n\\s+?\\S+",
            permission = PermissionType.ALL
    )
    private void addRole(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/r\\s+?", "");
        String[] split = s.split("\\s+?-");
        String name = "";
        String prompt = "";
        for (String ss : split) {
            ss = ss.replaceFirst("\\s+", "").replace("-", "");
            String substring = ss.substring(1);
            if (ss.startsWith("a")) {
                name = substring;
            } else if (ss.startsWith("n")) {
                prompt = substring;
            }
        }
        Role role = new Role(name, prompt);
        ROLE_CONFIG.addRole(role);
    }

    @Command(
            name = "OpenAI",
            dec = "保存人设",
            help = "/r 1",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/r\\s+save",
            permission = PermissionType.ALL
    )
    private void saveRole(MessageEvent event) throws ConfigWriteException {
        ROLE_CONFIG.save();
    }

    @Command(
            name = "OpenAI",
            dec = "列出所有人设",
            help = "/r all",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/r\\s+all",
            permission = PermissionType.ALL
    )
    private void listAllRole(MessageEvent event) {
        List<Role> roles = ROLE_CONFIG.getRoles();
        ForwardMessageBuilder builder = new ForwardMessageBuilder(event.getSubject());
        Bot bot = event.getBot();
        String senderName = "Numeron";
        builder.add(bot.getId(), senderName, new PlainText("帮助文档"));
        roles.forEach(t -> {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.append(t.getName());
            builder.add(bot.getId(), senderName, messageChainBuilder.build());
        });
        event.getSubject().sendMessage(builder.build());
    }


    @Command(
            name = "OpenAI",
            dec = "清除对话",
            help = "/reset",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            text = "/reset",
            permission = PermissionType.ALL
    )
    private void clear(MessageEvent event) {
        CONVERSATION = new Conversation(OpenAiServiceImpl.INSTANCE.OPENAICONFIG.getLimit());
    }

    @Command(
            name = "OpenAI",
            dec = "聊天",
            help = "/c [message]",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/c\\s+.+",
            permission = PermissionType.ALL
    )
    private void chat(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/c\\s+", "");
        ChatMessage message = null;
        try {
            message = OpenAiServiceImpl.INSTANCE.OPENAISERVICE.createChatCompletion(createRequest(s)).getChoices().get(0).getMessage();
            CONVERSATION.add(message);
        } catch (HttpException e) {
            CONVERSATION.reduce();
            NumeronLogUtil.logger.error("ERROR", e);
        }
        if (message != null) {
            OpenAiServiceImpl.INSTANCE.sendMessage(event, message.getContent().replaceFirst("\\n\\n", "").replaceFirst("\\?", "").replaceFirst("？", ""));
        }

    }

    private ChatCompletionRequest createRequest(String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(content);
        chatMessage.setRole("system");
        CONVERSATION.add(chatMessage);
        ChatCompletionRequest request = CHAT_CONFIG.load();
        request.setMessages(CONVERSATION);
        return request;
    }
}
