# Numeron

- [Numeron](#numeron)
    - [介绍:](#介绍)
        - [模块:](#模块)
        - [实现的功能:](#实现的功能)
    - [说明:](#说明)
        - [@Message使用:](#message使用)
        - [@Event使用:](#event使用)
        - [用@Command生成指令表:](#用command生成指令表)
        - [用@Menu生成图片菜单:](#用menu生成图片菜单)
    - [开发计划:](#开发计划)
    - [联系方式:](#联系方式)

## 介绍:

这是一个使用mirai-core开发的qqBot

### 模块:

- core模块是脚手架(消息过滤实现)
- boot模块用于启动机器人
- menu模块用于图片菜单生成
- plugin模块是主要的功能
- buildSrc模块对项目没有用处, 只是用于打包可执行jar
- console模块(待开发)

## 实现的功能:

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
- 发送/help获取自动生成的命令表(在方法上加上`@Command`注解生成)
- 对接openai
    - 聊天
    - 补全
    - 问答
    - 绘画
- 游戏王查卡
- rss订阅
- 生成图片菜单目录

## 说明:

第一次使用会使用控制台输入配置, 登陆后则会自动登陆, 生成的文件逻辑看源码吧,

<b>将所有QQ机器人功能写在[numeron-plugin](numeron-plugin)模块下</b>

在消息事件处理的方法上打上对应注解就可以监听到符合规则的消息后自动执行

### @Message使用:

<code>[@Message](numeron-core/src/main/java/com/erzbir/numeron/core/handler/Message.java)</code>
> 可以标记在所有消息事件类型的处理方法上, 监听到满足此注解定义的规则的事件就会反射调用被标记的方法,
> 类上必须有<code>[@Listener](numeron-core/src/main/java/com/erzbir/numeron/core/listener/Listener.java)</code>标记
>
> 这个注解适合用于命令类型的监听(给机器人发送一个消息, 进行相关代码运行),
> 如果不是消息事件则使用下文介绍的<code>[@Event](numeron-core/src/main/java/com/erzbir/numeron/core/handler/Event.java)</code>

```java
@Listener
public class Test {
    // 消息匹配规则设置了默认值, 默认是equals()完全匹配

    @Message(messageRule = MessageRule.REGEX, text = "\\d+", permission = PermissionType.ALL, filterRule = FilterRule.BLACKLIST)
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
    @Message(text = "/mute\\s+?@?(\\d+?) (\\d+)", filterRule = FilterRule.NONE, messageRule = MessageRule.REGEX, permission = PermissionType.MASTER)
    private void muteSingle(MessageEvent event) {
        String[] s = event.getMessage().contentToString().split("\\s+");
        long id;
        int time;
        s[1] = s[1].replaceAll("@", "");
        id = Long.parseLong(s[1]);
        time = Integer.parseInt(s[2]);
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

- [ ] console控制台
- [ ] 功能热加载和插件模式
- [ ] 只监听某个id(好友/群)的事件监听注册(用注解实现)
- [ ] 兼容mirai-console的插件
- [ ] 虚拟线程支持

## 联系方式:

email:

- 2978086497@qq.com
