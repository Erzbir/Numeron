# Numeron

- [Numeron](#numeron)
    - [介绍:](#介绍)
        - [模块:](#模块)
        - [实现的功能:](#plugin-模块实现的功能)
    - [说明:](#说明)
        - [只使用 bot 功能:](#只使用-bot-功能)
            - [@Handler 使用:](#handler使用)
            - [@Filter 使用:](#filter-使用)
            - [@Permission 使用](#permission-使用)
            - [用@Command生成指令表:](#command-生成指令表)
            - [用@Menu生成图片菜单:](#menu-生成图片菜单)
    - [开发计划:](#开发计划)
    - [联系方式:](#联系方式)

## 介绍:

这是一个使用 mirai-core 开发的 qqBot, 添加了注解风格编程

这个项目目前打算做成通用 bot 框架, 与具体的 bot 协议框架解耦

有很多没有写进文档, 很多类也没有注释, 待补充......

### 模块:

- core: 脚手架(消息过滤和监听注册实现)
- api: 监听注册等 api
- mirai-bot: mirai 的实现 (计划做成适配器)
- boot: 定义程序的启动
- menu: 图片菜单生成
- plugin: 实现的 bot 功能 (实际上应该作为此项目的一个子模块)
- console: 控制台(待开发)和插件加载
- utils: 一些通用工具

因为打算与具体的 bot 框架分离, 结构可能会出现较大变化

## plugin 模块实现的功能:

- 消息回复
- @禁言, qq号禁言
- 全体禁言
- 黑名单检测
- 违禁词检测
- 精准问答
- 防撤回
- 入群欢迎/退群反馈
- 代码和命令执行, 支持JavaScript/Python/Shell
- 群签到功能
- 发送/help获取自动生成的命令表(在方法上加上 `@Command` 注解生成)
- 对接openai
    - 聊天
    - 补全
    - 问答
    - 绘画
- 游戏王查卡
- rss订阅
- 生成图片菜单目录

## 说明:

### 只使用 bot 功能:

此部分由 mirai-core 提供底层实现

第一次使用会使用控制台输入配置, 登陆后则会自动登陆, 提供多 bot 支持

在 [mirai-bot](mirai-bot) 模块运行主函数即可

<b>所有QQ机器人功能都写在[numeron-plugin](numeron-plugin)模块下</b>

### 脚手架使用:

[@Component](numeron-api/src/main/java/com/erzbir/numeron/annotation/Component.java) 注解表示这个类需要注册到容器中
[@Listener](numeron-api/src/main/java/com/erzbir/numeron/annotation/Listener.java) 注解表示这个类需要被当作 "监听器"
注册到容器中

如果作为脚手架使用, 必须使用 [numeron-boot](numeron-boot) 模块, 使用 `StarterController`
将项目的启动类作为参数调用 `boot()` 方法

```java
public class NumeronBotApplication {

    public static void main(String[] args) {
        StarterController starterController = new StarterController();
        starterController.boot(NumeronBotApplication.class, NumeronBotApplication.class.getClassLoader());  // 调用 boot 方法初始化
        BotServiceImpl.INSTANCE.loginAll();
    }
}
```

#### @Handler使用:

<code>[@Handler](numeron-api/src/main/java/com/erzbir/numeron/annotation/Handler.java)</code>

> 将一个方法标记为监听回调, 监听的时间类型由参数决定


此注解标注的方法由 <code>[ListenerRegisterInter](numeron-api/src/main/java/com/erzbir/numeron/api/listener/ListenerRegisterInter.java)</code>
的实现类注册到 bot 框架监听

```java

@Listener
public class Test {

    /**
     * bot 收到群消息则会触发这个方法
     */
    @Handler
    private void test(GroupMessageEvent event) {
        event.getSubject().sendMessage("收到了群消息");
    }
}
```

#### @Filter 使用:

<code>[@Filter](numeron-api/src/main/java/com/erzbir/numeron/annotation/Filter.java)</code>

此注解可以针对: 群 id, 事件发送者 id, bot id 以及消息事件的文本消息来进行规则过滤

此注解可以实现简单的权限, 本项目提供了一个专门的权限过滤注解, 下文介绍

```java

@Listener
public class Test {

    /**
     * bot 收到群消息 "hi" 则会触发这个方法
     */
    @Handler
    @Filter("hi")
    private void test(GroupMessageEvent event) {
        event.getSubject().sendMessage("你好");
    }
}
```

#### @Permission 使用:

此项目中提供了一个默认的权限实现, 也可以通过 spi
实现 [PermissionService](numeron-api/src/main/java/com/erzbir/numeron/api/permission/PermissionService.java)
接口实现自定义权限规则 (尚未开发完毕)

```java

@Listener
public class Test {

    /**
     * bot 收到主人发送的群消息 "hi" 则会触发这个方法
     */
    @Handler
    @Filter("hi")
    @Permission(permission = PermissionType.MASTER)
    private void test(GroupMessageEvent event) {
        event.getSubject().sendMessage("主人你好");
    }
}
```

#### @Command 生成指令表:
此部分打算抽象一个 format 类负责格式化

<code>[@Command](numeron-api/src/main/java/com/erzbir/numeron/annotation/Command.java)</code>

```java

@Listener
class Test {
    @Command(
            name = "自动回复",
            dec = "添加关键词回复",
            help = "/learn ques answer"
    )
    @Handler
    @Filter(value = "^/learn\\s+?.*?\\s+?.*", matchType = MatchType.REGEX_MATCHES)
    private void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        if (split.length < 3) {
            return;
        }
        AutoReplyData.INSTANCE.add(split[1], split[2], e.getSender().getId());
        e.getSubject().sendMessage("学会了");
    }

    @Command(
            name = "自动回复",
            dec = "删除关键词回复",
            help = "/forget ques"
    )
    @Handler
    @Filter(value = "^/forget\\s+\\S+", matchType = MatchType.REGEX_MATCHES)
    private void forget(MessageEvent e) {
        String s = e.getMessage().contentToString().replaceAll("\\s+", "");
        if (s.isEmpty()) {
            return;
        }
        AutoReplyData.INSTANCE.remove(s);
        e.getSubject().sendMessage("忘掉了");
    }
}
```

### @Menu 生成图片菜单:

此部分打算抽象一个 format 类负责格式化

<code>[@Menu](numeron-menu/src/main/java/com/erzbir/numeron/menu/Menu.java)</code>


> 有 @Command 会为这个 menu 生成帮助

```java

@Listener
@Menu(name = "学习对话")
class Test {
    @Command(
            name = "自动回复",
            dec = "添加关键词回复",
            help = "/learn ques answer"
    )
    @Handler
    @Filter(value = "^/learn\\s+?.*?\\s+?.*", matchType = MatchType.REGEX_MATCHES)
    private void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        if (split.length < 3) {
            return;
        }
        AutoReplyData.INSTANCE.add(split[1], split[2], e.getSender().getId());
        e.getSubject().sendMessage("学会了");
    }

    @Command(
            name = "自动回复",
            dec = "删除关键词回复",
            help = "/forget ques"
    )
    @Handler
    @Filter(value = "^/forget\\s+\\S+", matchType = MatchType.REGEX_MATCHES)
    private void forget(MessageEvent e) {
        String s = e.getMessage().contentToString().replaceAll("\\s+", "");
        if (s.isEmpty()) {
            return;
        }
        AutoReplyData.INSTANCE.remove(s);
        e.getSubject().sendMessage("忘掉了");
    }
}
```

## 开发计划:

- [ ] 群管理功能
- [ ] console控制台
- [ ] 与 bot 框架解耦
- [x] 功能热加载
- [x] 插件模式

## 联系方式:

email:

- erzbir@mail.com
