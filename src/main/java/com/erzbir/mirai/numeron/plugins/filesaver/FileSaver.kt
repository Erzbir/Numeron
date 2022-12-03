package com.erzbir.mirai.numeron.plugins.filesaver

import com.erzbir.mirai.numeron.configs.GlobalConfig
import com.erzbir.mirai.numeron.plugins.Plugin
import com.erzbir.mirai.numeron.plugins.PluginRegister
import com.erzbir.mirai.numeron.processor.Command
import com.erzbir.mirai.numeron.store.DefaultStore
import com.erzbir.mirai.numeron.store.RedisStore
import com.erzbir.mirai.numeron.utils.NetUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact.Companion.uploadImage
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.file.AbsoluteFileFolder.Companion.extension
import net.mamoe.mirai.event.EventChannel
import net.mamoe.mirai.event.events.BotEvent
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.message.data.Image.Key.queryUrl
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.absolutePathString

@Plugin
object FileSaver : PluginRegister {
    private var autoPick = false
    private val storeLocation = Path.of(GlobalConfig.botDir, "qq_files")
    private val picStoreLocation: Path = Path.of(storeLocation.absolutePathString(), "pic")
    private val fileStoreLocation: Path = Path.of(storeLocation.absolutePathString(), "file")
    private val audioStoreLocation: Path = Path.of(storeLocation.absolutePathString(), "audio")

    init {
        if (!Files.exists(storeLocation)) {
            Files.createDirectory(storeLocation)
        }
        if (!Files.exists(picStoreLocation)) {
            Files.createDirectory(picStoreLocation)
        }
        if (!Files.exists(fileStoreLocation)) {
            Files.createDirectory(fileStoreLocation)
        }
        if (!Files.exists(audioStoreLocation)) {
            Files.createDirectory(audioStoreLocation)
        }
    }

    @Command(name = "文件保存和发送", dec = "图片/文件/文本", help = "/fput  /fget  /dl")
    override fun register(bot: Bot, channel: EventChannel<BotEvent>) {
        channel.subscribeAlways<MessageEvent> { it ->
            val ids = it.source.ids
            val message1 = it.message
            val defaultStore = DefaultStore.getInstance()
            val redisStore = RedisStore.getInstance()
            defaultStore.save(ids[0], message1)
            val sender = it.sender.id
            val content = it.message.contentToString()
            if (autoPick) {
                for (m in message1) {
                    if (m is Image) {
                        val picUrl = m.queryUrl()
                        try {
                            NetUtil.downloadTo(picUrl, File(picStoreLocation.absolutePathString(), m.imageId))
                            redisStore.set("pic_" + m.imageId, m.imageId, -1L)
                        } catch (e: Exception) {
                            e.printStackTrace()
                            return@subscribeAlways
                        }
                    } else if (m is PlainText) {
                        try {
                            redisStore.set("plain_" + Date().time.toString(), m.content, -1L)
                        } catch (e: Exception) {
                            return@subscribeAlways
                        }
                    } else if (m is FileMessage) {
                        if (it.subject is Group) {
                            val file = m.toAbsoluteFile(it.subject as Group)
                            file?.let { f ->
                                try {
                                    NetUtil.downloadTo(
                                        f.getUrl()!!,
                                        File(fileStoreLocation.absolutePathString(), file.name)
                                    )
                                    redisStore.set("file_" + file.id, file.name, -1L)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    return@subscribeAlways
                                }
                            }
                        }

                    }
                }
            }
            when {
                content.startsWith("/fput") -> {
                    if (!GlobalConfig.whiteList.contains(sender)) {
                        return@subscribeAlways
                    }
                    val command = content.split(" ").filter { it.isNotEmpty() }
                    if (command.size != 2) {
                        it.subject.sendMessage("Usage: /fput [filename]")
                        return@subscribeAlways
                    }
                    val ids1 = it.message[QuoteReply.Key]!!.source.ids
                    val find = defaultStore.find(ids1[0])
                    if (find == null) {
                        it.subject.sendMessage("你选中的消息无法找到")
                        return@subscribeAlways
                    }
                    println(find)
                    find.let { m ->
                        var saved = false
                        for (ele in m) {
                            if (ele is Image) {
                                val picUrl = ele.queryUrl()
                                try {
                                    NetUtil.downloadTo(
                                        picUrl,
                                        File(
                                            picStoreLocation.absolutePathString(),
                                            "${command[1]}.${ele.imageType.name}"
                                        )
                                    )
                                    redisStore.set(
                                        "pic_" + ele.imageId,
                                        "${command[1]}.${ele.imageType.name.lowercase(Locale.getDefault())}",
                                        -1L
                                    )
                                    saved = true
                                } catch (e: Exception) {
                                    it.subject.sendMessage(
                                        PlainText("图片保存失败").plus(
                                            PlainText("\n").plus(
                                                PlainText(
                                                    e.message ?: "未知错误"
                                                )
                                            )
                                        )
                                    )
                                    return@subscribeAlways
                                }
                                it.subject.sendMessage("保存图片成功")
                            }
                            if (ele is PlainText) {
                                try {
                                    redisStore.set("plain_" + Date().time.toString(), ele.content, -1L)
                                } catch (e: Exception) {
                                    it.subject.sendMessage(
                                        PlainText("文本保存失败").plus(
                                            PlainText("\n").plus(
                                                PlainText(
                                                    e.message ?: "未知错误"
                                                )
                                            )
                                        )
                                    )
                                    return@subscribeAlways
                                }
                                it.subject.sendMessage("保存文本成功")
                            }
                            if (ele is Audio) {
                                return@subscribeAlways
                            }
                            if (ele is FileMessage) {
                                if (it.subject is Group) {
                                    val file = ele.toAbsoluteFile(it.subject as Group)
                                    file?.let { f ->
                                        try {
                                            NetUtil.downloadTo(
                                                f.getUrl()!!,
                                                File(
                                                    fileStoreLocation.absolutePathString(),
                                                    "${command[1]}.${file.extension}"
                                                )
                                            )
                                            redisStore.set(
                                                "file_" + ele.id, "${command[1]}.${
                                                    file.extension.lowercase(
                                                        Locale.getDefault()
                                                    )
                                                }", -1L
                                            )
                                            saved = true
                                        } catch (e: Exception) {
                                            it.subject.sendMessage(
                                                PlainText("文件保存失败").plus(
                                                    PlainText("\n").plus(
                                                        PlainText(e.message ?: "未知错误")
                                                    )
                                                )
                                            )
                                            return@subscribeAlways
                                        }
                                        it.subject.sendMessage("保存文件成功")
                                    }
                                }

                            }
                        }
                        if (!saved) {
                            it.subject.sendMessage("你选中的消息没有可以保存的内容")
                        }
                    }
                }

                content.startsWith("/fget") -> {
                    if (!GlobalConfig.whiteList.contains(sender)) {
                        return@subscribeAlways
                    }
                    val command = content.split(" ").filter { it.isNotEmpty() }
                    if (command.size != 3 && command.size != 5) {
                        it.subject.sendMessage("Usage: /fget [pic|file] [filename] or /get [pic|file] [filename] [width] [height]")
                        return@subscribeAlways
                    }
                    when (command[1]) {
                        "pic" -> {
                            if (Files.exists(Path.of(picStoreLocation.absolutePathString(), command[2]))) {
                                try {
                                    val uploadImage = it.subject.uploadImage(
                                        File(
                                            Path.of(picStoreLocation.absolutePathString(), command[2])
                                                .absolutePathString()
                                        )
                                    )
                                    if (command.size == 5) {
                                        val newBuilder = Image.Builder.newBuilder(uploadImage.imageId)
                                        newBuilder.width = command[3].toInt()
                                        newBuilder.height = command[4].toInt()
                                        println("使用这个")
                                        it.subject.sendMessage(newBuilder.build())
                                        return@subscribeAlways
                                    }
                                    it.subject.sendMessage(uploadImage)
                                } catch (e: Exception) {
                                    it.subject.sendMessage(
                                        PlainText("文件发送失败").plus(
                                            PlainText("\n").plus(
                                                PlainText(
                                                    e.message ?: "未知错误"
                                                )
                                            )
                                        )
                                    )
                                    return@subscribeAlways
                                }
                            } else {
                                it.subject.sendMessage("没有这张图片")
                            }
                        }

                        "file" -> {
                            if (Files.exists(Path.of(fileStoreLocation.absolutePathString(), command[2]))) {
                                if (it.subject is Group) {
                                    try {
                                        val toExternalResource = File(
                                            Path.of(fileStoreLocation.absolutePathString(), command[2])
                                                .absolutePathString()
                                        ).toExternalResource()
                                        (it.subject as Group).files.uploadNewFile(command[2], toExternalResource)
                                    } catch (e: Exception) {
                                        it.subject.sendMessage(
                                            PlainText("文件发送失败").plus(
                                                PlainText("\n").plus(
                                                    PlainText(e.message ?: "未知错误")
                                                )
                                            )
                                        )
                                        return@subscribeAlways
                                    }
                                } else {
                                    it.subject.sendMessage("只支持向群聊发送文件")
                                }
                            } else {
                                it.subject.sendMessage("没有这个文件")
                            }
                        }
                    }
                }

                content.startsWith("/fls") -> {
                    if (!GlobalConfig.whiteList.contains(sender)) {
                        return@subscribeAlways
                    }
                    val command = content.split(" ").filter { it.isNotEmpty() }
                    if (command.size != 2) {
                        it.subject.sendMessage("Usage: /fls [pic|file|plain]")
                        return@subscribeAlways
                    }
                    when (command[1]) {
                        "pic" -> {
                            val f = picStoreLocation.toFile().list()
                            if (f == null || f.isEmpty()) {
                                it.subject.sendMessage("暂无文件")
                            } else {
                                it.subject.sendMessage(Arrays.toString(f).replace(",", "\n"))
                            }
                        }

                        "file" -> {
                            val f = fileStoreLocation.toFile().list()
                            if (f == null || f.isEmpty()) {
                                it.subject.sendMessage("暂无文件")
                            } else {
                                it.subject.sendMessage(Arrays.toString(f).replace(",", "\n"))
                            }
                        }

                        "plain" -> {
                            val m = redisStore.plain.joinToString("\n")
                            if (m.isEmpty()) {
                                it.subject.sendMessage("暂无文本")
                            } else {
                                it.subject.sendMessage(m)
                            }
                        }
                    }
                }

                content.startsWith("/auto_pick") -> {
                    if (!GlobalConfig.whiteList.contains(sender)) {
                        return@subscribeAlways
                    }
                    val command = content.split(" ").filter { it.isNotEmpty() }
                    if (command.size != 2) {
                        it.subject.sendMessage("Usage: /auto_pick [true|false]")
                        return@subscribeAlways
                    }
                    when (command[1]) {
                        "true" -> {
                            autoPick = true
                            it.subject.sendMessage("已开启自动保存")
                        }

                        "false" -> {
                            autoPick = false
                            it.subject.sendMessage("已关闭自动保存")
                        }
                    }
                }

                content.startsWith("/dl") -> {
                    if (!GlobalConfig.whiteList.contains(sender)) {
                        return@subscribeAlways
                    }
                    val command = content.split(" ").filter { it.isNotEmpty() }
                    if (command.size != 3) {
                        it.subject.sendMessage("Usage: /dl [url] [filename]")
                        return@subscribeAlways
                    }
                    it.subject.sendMessage("正在下载文件...")
                    try {
                        NetUtil.downloadTo(command[1], File(fileStoreLocation.absolutePathString(), command[2]))
                    } catch (e: Exception) {
                        it.subject.sendMessage(
                            PlainText("文件下载失败").plus(
                                PlainText("\n").plus(
                                    PlainText(
                                        e.message ?: "未知错误"
                                    )
                                )
                            )
                        )
                        return@subscribeAlways
                    }
                    redisStore.set("file_${command[2]}", command[2], -1L)
                    it.subject.sendMessage("文件${command[2]}下载完成")
                }

                content.startsWith("/frm") -> {
                    if (!GlobalConfig.whiteList.contains(sender)) {
                        return@subscribeAlways
                    }
                    val command = content.split(" ")
                    if (command.size != 3) {
                        it.subject.sendMessage("Usage: /frm [pic|file|plain] [filename]")
                        return@subscribeAlways
                    }
                    when (command[1]) {
                        "pic" -> {
                            withContext(Dispatchers.IO) {
                                try {
                                    Files.delete(Path.of(picStoreLocation.absolutePathString(), command[2]))
                                } catch (e: IOException) {
                                    it.subject.sendMessage("删除失败 ${e.message}")
                                    return@withContext
                                }
                            }
                            redisStore.removePic(command[2])
                            it.subject.sendMessage("删除图片[${command[2]}]成功")
                        }

                        "file" -> {
                            withContext(Dispatchers.IO) {
                                try {
                                    Files.delete(Path.of(fileStoreLocation.absolutePathString(), command[2]))
                                } catch (e: IOException) {
                                    it.subject.sendMessage("删除失败 ${e.message}")
                                    return@withContext
                                }
                            }
                            redisStore.removeFile(command[2])
                            it.subject.sendMessage("删除文件[${command[2]}]成功")
                        }

                        "plain" -> {
                            redisStore.removePlain(command[2])
                            it.subject.sendMessage("删除文本[${command[2]}]成功")
                        }
                    }
                }
            }
        }
    }
}
