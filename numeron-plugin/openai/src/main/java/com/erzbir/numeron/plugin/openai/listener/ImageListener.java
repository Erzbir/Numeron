package com.erzbir.numeron.plugin.openai.listener;

import com.erzbir.numeron.annotation.Command;
import com.erzbir.numeron.annotation.Listener;
import com.erzbir.numeron.annotation.Message;
import com.erzbir.numeron.filter.FilterRule;
import com.erzbir.numeron.filter.MessageRule;
import com.erzbir.numeron.filter.PermissionType;
import com.erzbir.numeron.menu.Menu;
import com.erzbir.numeron.plugin.openai.OpenAiServiceImpl;
import com.erzbir.numeron.plugin.openai.ParseImage;
import com.erzbir.numeron.plugin.openai.config.ImageConfig;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.image.Image;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.QuoteReply;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

/**
 * @author Erzbir
 * @Date: 2023/6/19 21:47
 */
@Listener
@Menu(name = "openai")
@SuppressWarnings("unused")
public class ImageListener {
    private final ImageConfig IMAGE_CONFIG = ImageConfig.getInstance();

    @Command(
            name = "OpenAI",
            dec = "画图",
            help = "/i 美女",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/i\\s+.+",
            permission = PermissionType.ALL
    )
    private void image(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/i\\s+", "");
        MessageChain singleMessages = buildImageMessage(OpenAiServiceImpl.INSTANCE.OPENAISERVICE.createImage(createRequest(s)).getData(), event);
        event.getSubject().sendMessage(singleMessages);
    }

    @Command(
            name = "OpenAI",
            dec = "清理保存图片",
            help = "/pic clean",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            text = "/pic clean",
            permission = PermissionType.ALL
    )
    private void clean(MessageEvent event) {
        String folder = ImageConfig.getInstance().getFolder();
        File file = new File(folder);
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            f.delete();
        }
    }

    @Command(
            name = "OpenAI",
            dec = "关闭图片保存",
            help = "/pic save [true|false]",
            permission = PermissionType.ALL
    )
    @Message(
            filterRule = FilterRule.BLACK,
            messageRule = MessageRule.REGEX,
            text = "^/pic\\s+save\\s+[true|false]",
            permission = PermissionType.ALL
    )
    private void picSave(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("^/pic\\s+save\\s+", "");
        ImageConfig.getInstance().setSave(Boolean.parseBoolean(s));
    }

    private MessageChain buildImageMessage(List<Image> images, MessageEvent event) {
        MessageChainBuilder chainBuilder = new MessageChainBuilder();
        for (Image image : images) {
            net.mamoe.mirai.message.data.Image image1 = Contact.uploadImage(event.getSubject(), new ByteArrayInputStream(ParseImage.store(image, IMAGE_CONFIG.getFolder(), IMAGE_CONFIG.isSave())));
            if (OpenAiServiceImpl.INSTANCE.OPENAICONFIG.isChat_by_at()) {
                chainBuilder.append(new At(event.getSender().getId()).plus(image1));
            } else if (OpenAiServiceImpl.INSTANCE.OPENAICONFIG.isReply()) {
                chainBuilder.append(new QuoteReply(event.getMessage()).plus(image1)).build();
            } else {
                chainBuilder.append(image1).build();
            }
        }
        return chainBuilder.build();
    }

    private CreateImageRequest createRequest(String content) {
        CreateImageRequest request = IMAGE_CONFIG.load();
        request.setPrompt(content);
        return request;
    }
}
