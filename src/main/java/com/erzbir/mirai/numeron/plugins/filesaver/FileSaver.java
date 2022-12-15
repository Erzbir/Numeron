package com.erzbir.mirai.numeron.plugins.filesaver;

import com.erzbir.mirai.numeron.configs.entity.WhiteList;
import com.erzbir.mirai.numeron.plugins.Plugin;
import com.erzbir.mirai.numeron.plugins.PluginRegister;
import com.erzbir.mirai.numeron.processor.Command;
import com.erzbir.mirai.numeron.store.DefaultStore;
import com.erzbir.mirai.numeron.store.RedisStore;
import com.erzbir.mirai.numeron.utils.NetUtil;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.file.AbsoluteFile;
import net.mamoe.mirai.contact.file.AbsoluteFileFolder;
import net.mamoe.mirai.event.EventChannel;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Erzbir
 * @Date: 2022/12/12 20:41
 * 由群友提供kt代码, 我改为java代码
 */
@Plugin
public class FileSaver implements PluginRegister {
    private final String mainDir = "erzbirnumeron/";
    private final String botDir = mainDir + "bots/";
    private final Path storeLocation = Path.of(botDir, "qq_files");
    private final Path picStoreLocation = Path.of(storeLocation.toAbsolutePath().toString(), "pic");
    private final Path fileStoreLocation = Path.of(storeLocation.toAbsolutePath().toString(), "file");
    private final Path audioStoreLocation = Path.of(storeLocation.toAbsolutePath().toString(), "audio");
    private boolean autoPick = false;

    {
        try {

            if (!Files.exists(storeLocation)) {
                Files.createDirectory(storeLocation);
            }
            if (!Files.exists(picStoreLocation)) {
                Files.createDirectory(picStoreLocation);
            }
            if (!Files.exists(fileStoreLocation)) {
                Files.createDirectory(fileStoreLocation);
            }
            if (!Files.exists(audioStoreLocation)) {
                Files.createDirectory(audioStoreLocation);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Command(name = "文件保存和发送", dec = "图片/文件/文本", help = "/fput  /fget  /dl")
    @Override
    public void register(Bot bot, EventChannel<BotEvent> channel) {
        channel.subscribeAlways(MessageEvent.class, it -> {
            int[] ids = it.getSource().getIds();
            MessageChain message = it.getMessage();
            DefaultStore defaultStore = DefaultStore.getInstance();
            RedisStore redisStore = RedisStore.getInstance();
            defaultStore.save(ids[0], message);
            long sender = it.getSender().getId();
            String content = message.contentToString();
            Contact contact = it.getSubject();
            if (autoPick) {
                for (Message m : message) {
                    if (m instanceof Image pic) {
                        String picUrl = Image.queryUrl(pic);
                        try {
                            NetUtil.downloadTo(picUrl, new File(picStoreLocation.toString(), pic.getImageId()));
                            redisStore.set("pic_" + pic.getImageId(), pic.getImageId(), -1L);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    } else if (m instanceof PlainText plain) {
                        try {
                            redisStore.set("plain_" + new Date().getTime(), plain.contentToString(), -1L);
                        } catch (Exception e) {
                            return;
                        }
                    } else if (m instanceof FileMessage file) {
                        if (contact instanceof Group group) {
                            AbsoluteFile file2 = file.toAbsoluteFile(group);
                            if (file2 == null) {
                                return;
                            }
                            NetUtil.downloadTo(file2.getUrl(), new File(fileStoreLocation.toString(), file2.getName()));
                            redisStore.set("file_" + file2.getId(), file2.getName(), -1L);
                        }
                    }
                }
            }
            if (content.startsWith("/fput")) {
                if (!WhiteList.INSTANCE.contains(sender)) {
                    return;
                }
                String[] command = content.split("\\s+?");
                if (command.length != 2) {
                    contact.sendMessage("Usage: /fput [filename]");
                    return;
                }
                int[] ids1 = Objects.requireNonNull(message.get(QuoteReply.Key)).getSource().getIds();
                MessageChain find = defaultStore.find(ids1[0]);
                if (find == null) {
                    contact.sendMessage("你选中的消息无法找到");
                    return;
                }
                boolean saved = false;
                for (Message ele : find) {
                    if (ele instanceof Image image) {
                        String picUrl = Image.queryUrl(image);
                        try {
                            NetUtil.downloadTo(picUrl, new File(picStoreLocation.toString(), command[1] + "." + image.getImageType().name()));
                            redisStore.set("pic_" + image.getImageId(), command[1] + "." + image.getImageType().name().toLowerCase(Locale.getDefault()), -1L);
                            saved = true;
                        } catch (Exception e) {
                            contact.sendMessage(
                                    new PlainText("图片保存失败").plus(
                                            new PlainText("\n").plus(
                                                    new PlainText(e.getMessage()))));
                            return;
                        }
                        contact.sendMessage("保存图片成功");
                    }
                    if (ele instanceof PlainText plain) {
                        try {
                            redisStore.set("plain_" + new Date().getTime(), plain.contentToString(), -1L);
                        } catch (Exception e) {
                            contact.sendMessage(
                                    new PlainText("文本保存失败").plus(
                                            new PlainText("\n").plus(
                                                    new PlainText(e.getMessage()))));
                            return;
                        }
                        contact.sendMessage("保存文本成功");
                    }
                    if (ele instanceof Audio audio) {
                        return;
                    }
                    if (ele instanceof FileMessage file) {
                        if (contact instanceof Group group) {
                            AbsoluteFile file2 = file.toAbsoluteFile(group);
                            try {
                                if (file2 == null) {
                                    return;
                                }
                                NetUtil.downloadTo(
                                        file2.getUrl(),
                                        new File(fileStoreLocation.toString(), command[1] + "." + AbsoluteFileFolder.Companion.getExtension(file2)));
                                redisStore.set("file_" + file.getId(),
                                        command[1] + "." + AbsoluteFileFolder.Companion.getExtension(file2).toLowerCase(Locale.getDefault()), -1L);
                                saved = true;
                            } catch (Exception e) {
                                contact.sendMessage(new PlainText("文件保存失败")
                                        .plus(new PlainText("\n")
                                                .plus(new PlainText(e.getMessage()))));
                                return;
                            }
                            contact.sendMessage("保存文件成功");
                        }

                    }
                    if (!saved) {
                        contact.sendMessage("你选中的消息没有可以保存的内容");
                    }
                }
            }


            if (content.startsWith("/fget")) {
                if (!WhiteList.INSTANCE.contains(sender)) {
                    return;
                }
                String[] command = content.split("\\s+");
                if (command.length != 3 && command.length != 5) {
                    contact.sendMessage("Usage: /fget [pic|file] [filename] or /get [pic|file] [filename] [width] [height]");
                    return;
                }
                switch (command[1]) {
                    case "pic" -> {
                        if (Files.exists(Path.of(picStoreLocation.toString(), command[2]))) {
                            try {
                                Image uploadImage = Contact.uploadImage(contact, new File(Path.of(picStoreLocation.toString(), command[2]).toString()));
                                if (command.length == 5) {
                                    Image.Builder newBuilder = Image.Builder.newBuilder(uploadImage.getImageId());
                                    newBuilder.setWidth(Integer.parseInt(command[3]));
                                    newBuilder.setHeight(Integer.parseInt(command[4]));
                                    contact.sendMessage(newBuilder.build());
                                    return;
                                }
                                contact.sendMessage(uploadImage);
                            } catch (Exception e) {
                                contact.sendMessage(new PlainText("文件发送失败")
                                        .plus(new PlainText("\n")
                                                .plus(new PlainText(e.getMessage()))));
                                return;
                            }
                        } else {
                            contact.sendMessage("没有这张图片");
                        }
                    }

                    case "file" -> {
                        if (Files.exists(Path.of(fileStoreLocation.toString(), command[2]))) {
                            if (contact instanceof Group group) {
                                try {
                                    ExternalResource toExternalResource = ExternalResource.create(new File(Path.of(fileStoreLocation.toString(), command[2]).toAbsolutePath().toString()));
                                    group.getFiles().uploadNewFile(command[2], toExternalResource);
                                } catch (Exception e) {
                                    contact.sendMessage(
                                            new PlainText("文件发送失败")
                                                    .plus(new PlainText("\n")
                                                            .plus(new PlainText(e.getMessage()))));
                                    return;
                                }
                            } else {
                                contact.sendMessage("只支持向群聊发送文件");
                            }
                        } else {
                            contact.sendMessage("没有这个文件");
                        }
                    }
                }
            }

            if (content.startsWith("/fls")) {
                if (!WhiteList.INSTANCE.contains(sender)) {
                    return;
                }
                String[] command = content.split("\\s+?");
                if (command.length != 2) {
                    contact.sendMessage("Usage: /fls [pic|file|plain]");
                    return;
                }
                switch (command[1]) {
                    case "pic" -> {
                        String[] f = picStoreLocation.toFile().list();
                        if (f == null || f.length == 0) {
                            contact.sendMessage("暂无文件");
                        } else {
                            contact.sendMessage(Arrays.toString(f).replace(",", "\n"));
                        }
                    }

                    case "file" -> {
                        String[] f = fileStoreLocation.toFile().list();
                        if (f == null || f.length == 0) {
                            contact.sendMessage("暂无文件");
                        } else {
                            contact.sendMessage(Arrays.toString(f).replace(",", "\n"));
                        }
                    }

                    case "plain" -> {
                        String m = redisStore.getPlain().toString().replaceAll(",", "\n");
                        if (m.isEmpty()) {
                            contact.sendMessage("暂无文本");
                        } else {
                            contact.sendMessage(m);
                        }
                    }
                }
            }

            if (content.startsWith("/auto_pick")) {
                if (!WhiteList.INSTANCE.contains(sender)) {
                    return;
                }
                String[] command = content.split("\\s+?");
                if (command.length != 2) {
                    contact.sendMessage("Usage: /auto_pick [true|false]");
                    return;
                }
                switch (command[1]) {
                    case "true" -> {
                        autoPick = true;
                        contact.sendMessage("已开启自动保存");
                    }

                    case "false" -> {
                        autoPick = false;
                        contact.sendMessage("已关闭自动保存");
                    }
                }
            }

            if (content.startsWith("/dl")) {
                if (!WhiteList.INSTANCE.contains(sender)) {
                    return;
                }
                String[] command = content.split("\\s+?");
                if (command.length != 3) {
                    contact.sendMessage("Usage: /dl [url] [filename]");
                    return;
                }
                contact.sendMessage("正在下载文件...");
                try {
                    NetUtil.downloadTo(command[1], new File(fileStoreLocation.toString(), command[2]));
                } catch (Exception e) {
                    contact.sendMessage(new PlainText("文件下载失败")
                            .plus(new PlainText("\n")
                                    .plus(new PlainText(e.getMessage()))));
                    return;
                }
                redisStore.set("file_${command[2]}", command[2], -1L);
                contact.sendMessage("文件" + command[2] + "下载完成");
            }

            if (content.startsWith("/frm")) {
                if (!WhiteList.INSTANCE.contains(sender)) {
                    return;
                }
                String[] command = content.split("\\s+");
                if (command.length != 3) {
                    contact.sendMessage("Usage: /frm [pic|file|plain] [filename]");
                    return;
                }
                switch (command[1]) {
                    case "pic" -> {

                        try {
                            Files.delete(Path.of(picStoreLocation.toString(), command[2]));
                            redisStore.removePic(command[2]);
                        } catch (Exception e) {
                            contact.sendMessage("删除失败 " + e.getMessage());
                            return;
                        }
                        contact.sendMessage("删除图片 " + command[2] + " 成功");
                    }

                    case "file" -> {
                        try {
                            Files.delete(Path.of(fileStoreLocation.toString(), command[2]));
                            redisStore.removeFile(command[2]);
                        } catch (Exception e) {
                            contact.sendMessage(e.getMessage());
                            return;
                        }
                        contact.sendMessage("删除文件[${command[2]}]成功");
                    }

                    case "plain" -> {
                        try {
                            redisStore.removePlain(command[2]);
                        } catch (Exception e) {
                            contact.sendMessage(e.getMessage());
                        }
                        contact.sendMessage("删除文本 " + command[2] + " 成功");
                    }
                }
            }
        });
    }
}

