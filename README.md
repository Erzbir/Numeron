# Numeron

- [介绍:](#介绍)
    - [模块:](#模块)
    - [实现的功能:](#plugin模块实现的功能)
- [说明:](#说明)
    - [启动项目:](#启动项目)
    - [配置文件:](#配置文件)
    - [API:](#api)
    - [插件模式:](#插件模式)
    - [@Message使用:](#message使用)
    - [@Event使用:](#event使用)
    - [用@Command生成指令表:](#用command生成指令表)
    - [用@Menu生成图片菜单:](#用menu生成图片菜单)
- [开发计划:](#开发计划)
- [联系方式:](#联系方式)

## 介绍:

这是一个使用 mirai-core 开发的 qqBot, 也可用作简单脚手架

**JDK 为 Oracle OpenJDK 17**

**构建系统为 Gradle (Kotlin DSL)**

### 模块:

- core: 脚手架(消息过滤和监听注册实现)
- boot: 启动机器人功能, 目前程序的入口在此模块下
- menu: 图片菜单生成
- plugin: 目前用于实现功能, 此模块下的类会在程序运行最初被加载, 可用于扩展功能(引入 core 模块即可)
- console: 控制台(待开发) 和插件模式
- api: core 的 api 接口
- utils: 一些通用工具
- deps: 用于引入第三方依赖

## plugin模块实现的功能:

- 消息回复
- @禁言, qq 号禁言
- 全体禁言
- 黑名单检测
- 违禁词检测
- 精准问答
- 防撤回
- 入群欢迎/退群反馈
- 代码和命令执行, 支持JavaScript/Python/Shell
- 群签到功能
- 发送/help获取自动生成的命令表(在方法上加上`@Command`注解生成)
- 对接openai
    - 聊天
        - 上下文
        - 人设
    - 补全
    - 问答
    - 绘画
- 游戏王查卡
- rss订阅
- 生成图片菜单目录

## 启动项目:

在 clone 代码并完成 gradle 构建后, 在 numeron-boot 模块下运行启动类的 `main()` 方法即可

第一次启动时会在命令行输入 bot 相关配置信息, 第一次启动默认为 ANDROID_WATCH 协议扫码且默认启用并登陆, 后续在
erzbirnumeron/config/botconfig.json 文件中可进行配置配置

## 说明:

### 配置文件:

#### botconfig.json:

```json
[
  {
    "account": 12412414,
    "password": "niqd123131",
    "enable": true,
    "master": 138312819,
    "heartbeatStrategy": "STAT_HB",
    "miraiProtocol": "ANDROID_WATCH",
    "loginType": "QR"
  },
  {
    "account": 329130123,
    "password": "oqdi1397121",
    "enable": false,
    "master": 138312819,
    "heartbeatStrategy": "STAT_HB",
    "miraiProtocol": "ANDROID_WATCH",
    "loginType": "PASSWORD"
  }
]
```

- account: bot 帐号
- password: bot 密码
- master: 主人帐号
- heartbeatStrategy: 心跳策略
- miraiProtocol: 登陆协议
- loginType: 登陆方式
    - 目前 mirai 只提供了密码和扫码 (只有 ANDROID_WATCH 支持) 两种, 如果为 QR (扫码) 则登陆协议必须为 ANDROID_WATCH
- enable: 是否启用
    - 如果 enable 为 false, 则只会创建此 bot 实例而不会登陆

### API:

- `BotService` 有关 Bot 的各种操作
- `AdminService` 群管理的相关操作
- `BlackService` 黑名单操作
- `GroupService` 授权群操作
- `WhiteService` 白名单操作
- `ListenerRegister` 监听注册器接口
- `Processor` 处理器接口
    - 实现此接口的类会在程序启动时或者所有 bot 关机并有一个 bot 重启时执行一次 `onApplicationEvent()`
    - 在所有 bot 关机时执行 `destroy()` 方法
- `Numeron` 程序接口
    - 获取此程序的各种配置信息
    - 可以通过此接口向注册监听方法的前后和监听回调函数执行前后插入方法
    - 增加 Processor 处理器(实现 `Processor` 接口的类), 增加处理器后行为和 `Processor` 的说明中除了不在启动时运行都一致.
      添加之后可以靠关闭全部 bot 并重启执行启动 `Processor`

### 插件模式:

实现 Plugin 接口, 目前插件打包时需包含所有依赖打包

将打包的插件 jar 放到运行目录的 numeron_plugins 目录下

### @Message 使用:

<code>[@Message](numeron-core/src/main/java/com/erzbir/numeron/core/handler/Message.java)</code>
> 可以标记在所有消息事件类型的处理方法上, 监听到满足此注解定义的规则的事件就会反射调用被标记的方法,
> 类上必须有<code>[@Listener](numeron-core/src/main/java/com/erzbir/numeron/core/listener/Listener.java)</code>标记
>
> 这个注解适合用于命令类型的监听(给机器人发送一个消息, 进行相关代码运行),
>
如果不是消息事件则使用下文介绍的<code>[@Event](numeron-core/src/main/java/com/erzbir/numeron/core/handler/Event.java)</code>

```java

@Listener
public class Test {
    // 消息匹配规则设置了默认值, 默认是equals()完全匹配

    @Message(messageRule = MessageRule.REGEX, text = "\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACK)
    // 处理群消息事件, 正则匹配模式, 匹配数字, 权限是所有人, 过滤规则是过滤掉黑名单
    private void regex(GroupMessageEvent event) {
        event.getSubject().sendMessage("这是一个数字");
    }

    @Message(text = "hi", permission = PermissionType.WHITE, filterRule = FilterRule.NONE)
    // 处理消息用户消息事件 匹配"hi", 权限是白名单, 不过滤
    private void sayHello(UserMessageEvent event) {
        event.getSubject().sendMessage("hi");
    }

    @Message(text = "晚安", permission = PermissionType.MASTER, filterRule = FilterRule.NORMAL)
    // 匹配消息事件 匹配"晚安", 权限是主人, 过滤掉groupList以外的群 
    private void sayGoodNight(MessageEvent event) {
        event.getSubject().sendMessage("晚安");
    }

    // 权限是所有人, 不过滤
    @Message(text = "你好", permission = PermissionType.ALL, filterRule = FilterRule.NONE)
    private void sayH(MessageEvent e) {
        e.getSubject().sendMessage("你好");
    }

    // 这是一个较为复杂的例子, 禁言一个人, 支持@和qq号
    @Message(
            text = "/mute\\s+?@?\\d+?\\s+?\\d+",
            filterRule = FilterRule.NONE,
            messageRule = MessageRule.REGEX,
            permission = PermissionType.ADMIN
    )
    private void muteSingle(MessageEvent event) {
        String s = event.getMessage().contentToString().replaceFirst("/unmute\\s+?@?", "");
        String[] ss = s.split("\\s+");
        long id;
        int time;
        id = Long.parseLong(ss[1]);
        time = Integer.parseInt(ss[2]);
        if (event instanceof GroupMessageEvent event1) {
            Objects.requireNonNull(event1.getGroup().get(id)).mute(time);
        } else {
            AtomicReference<NormalMember> member = new AtomicReference<>();
            GlobalConfig.groupList.forEach(v -> member.set(Objects.requireNonNull(event.getBot().getGroup(v)).get(id)));
            if (member.get().getPermission().getLevel() < 1) {
                member.get().mute(time);
            }
        }
    }
}
```

可以用消息处理注解做到什么?

- 三种过滤规则: 不过滤 / 过滤黑名单 / 正常过滤
- 四种权限规则: 主人 / 白名单 / 所有人 / 群管理员
- 六种消息匹配规则: 以?开头 / 以?结尾 / 包含? / 相等 / 正则 / 在数组中?

### @Event使用:

<code>[@Event](numeron-core/src/main/java/com/erzbir/numeron/core/handler/Event.java)</code>
和mirai提供的<code>@EventHandler</code>用法基本一样, 只是使用这个注解不用让类继承,
不过类上必须有<code>[@Listener](numeron-core/src/main/java/com/erzbir/numeron/core/listener/Listener.java)</code>标记

```java

@Listener
public class Test {

    @Event
    private void test(MessageRecallEvent.GroupRecall event) {
        System.out.println("有人撤回了消息");
    }
}
```

<code>[@Menu](numeron-menu/src/main/java/com/erzbir/numeron/menu/Menu.java)</code>
> 用于生成图片菜单(有@Command会为这个menu生成帮助)

<code>[@Command](numeron-core/src/main/java/com/erzbir/numeron/core/handler/Command.java)</code>
> 用于生成指令表

### 用@Command生成指令表:

```java

@Listener
class Test {
    @Command(
            name = "自动回复",
            dec = "添加关键词回复",
            help = "/learn ques answer",
            permission = PermissionType.ALL
    )
    @Message(
            messageRule = MessageRule.REGEX,
            text = "^/learn\\s+?.*?\\s+?.*",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        AutoReplyData.INSTANCE.add(split[1], split[2], e.getSender().getId());
        e.getSubject().sendMessage("学会了");
    }

    @Command(
            name = "自动回复",
            dec = "删除关键词回复",
            help = "/forget ques",
            permission = PermissionType.ALL
    )
    @Message(
            messageRule = MessageRule.REGEX,
            text = "^/forget\\s+?.*",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void forget(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        try {
            AutoReplyData.INSTANCE.remove(split[1], split[2]);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        e.getSubject().sendMessage("忘掉了");
    }
}
```

效果:
![2023031916.30.57](https://bloghexofluid.oss-cn-hangzhou.aliyuncs.com/uPic/2023031916.30.57.png)

### 用@Menu生成图片菜单:

> 有@Command会为这个menu生成帮助

```java

@Listener
@Menu(name = "学习对话")
class Test {
    @Command(
            name = "自动回复",
            dec = "添加关键词回复",
            help = "/learn ques answer",
            permission = PermissionType.ALL
    )
    @Message(
            messageRule = MessageRule.REGEX,
            text = "^/learn\\s+?.*?\\s+?.*",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void learn(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        AutoReplyData.INSTANCE.add(split[1], split[2], e.getSender().getId());
        e.getSubject().sendMessage("学会了");
    }

    @Command(
            name = "自动回复",
            dec = "删除关键词回复",
            help = "/forget ques",
            permission = PermissionType.ALL
    )
    @Message(
            messageRule = MessageRule.REGEX,
            text = "^/forget\\s+?.*",
            filterRule = FilterRule.BLACK,
            permission = PermissionType.ALL
    )
    private void forget(MessageEvent e) {
        String[] split = e.getMessage().contentToString().split("\\s+");
        try {
            AutoReplyData.INSTANCE.remove(split[1], split[2]);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        e.getSubject().sendMessage("忘掉了");
    }
}
```

效果:
![f44d0e472c5d15199ab8917cba107ace](https://bloghexofluid.oss-cn-hangzhou.aliyuncs.com/uPic/f44d0e472c5d15199ab8917cba107ace.png)

![9f7c0b37e82f492e8c282e54ddd7e755](https://bloghexofluid.oss-cn-hangzhou.aliyuncs.com/uPic/9f7c0b37e82f492e8c282e54ddd7e755.png)

## 开发计划:

- [ ] 群管理功能
- [x] api 和实现分离
- [ ] console 控制台
- [x] 功能热加载
- [x] 插件模式
- [ ] 只监听某个 id(好友/群) 的事件监听注册(用注解实现)

## 联系方式:

email:

- erzbir@mail.com
